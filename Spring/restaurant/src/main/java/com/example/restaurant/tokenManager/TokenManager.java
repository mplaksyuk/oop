package com.example.restaurant.tokenManager;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.restaurant.entity.User;
import com.example.restaurant.entity.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenManager {

    private static TokenManager instance;
    private static final Key ACCESS_SECRET  = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Key REFRESH_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    private static final long ACCESS_LIFE  = 60L * 60L * 1000L; // 1 min
    private static final long REFRESH_LIFE = 30L * 24L * 60L * 60L * 1000L; // 30 days

    private static List<String> refreshTokens = new ArrayList<>();

    private TokenManager() {}
    
    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }
    
    private static String generateToken(Integer id, Role role, Key secret, long life_time) {

        Date expiration = new Date(life_time + System.currentTimeMillis());

        String token = Jwts.builder()
                        .claim("id", id)
                        .claim("role", role.name())
                        .setExpiration(expiration)
                        .signWith(secret)
                        .compact();

        return token;
    }

    private static User parseToken(String token, Key secret) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);

            Integer id = claims.getBody().get("id", Integer.class);
            String role  = claims.getBody().get("role", String.class);

            User user = new User();

            user.setId(id);
            user.setRole(Role.valueOf(role));

            return user;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static void removeRefreshToken(String refreshToken) {
        refreshTokens.remove(refreshToken);
    }

    public static String generateAccessToken(Integer id, Role role) {
        return generateToken(id, role, ACCESS_SECRET, ACCESS_LIFE);
    }

    public static String generateRefreshToken(Integer id, Role role) {
        String refreshToken = generateToken(id, role, REFRESH_SECRET, REFRESH_LIFE);
        refreshTokens.add(refreshToken);
        return refreshToken;
    }

    public static Boolean verifyAccessToken(String accessToken) {
        User user = parseToken(accessToken, ACCESS_SECRET);
        return user != null;
    }

    public static Boolean verifyRefreshToken(String refreshToken) {
        if (!refreshTokens.contains(refreshToken)) {
            return false;
        }
        User user = parseToken(refreshToken, REFRESH_SECRET);
        return user != null;
    }

    public static User parseAccessToken(String accessToken) {
        return parseToken(accessToken, ACCESS_SECRET);
    }

    public static User parseRefreshToken(String refreshToken) {
        if (!refreshTokens.contains(refreshToken)) {
            return null;
        }
        return parseToken(refreshToken, REFRESH_SECRET);
    }    
}
