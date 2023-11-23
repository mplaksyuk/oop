package com.example.restaurant.controller;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.entity.User;
import com.example.restaurant.repository.UserRepository;
import com.example.restaurant.tokenManager.TokenManager;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserRepository ur;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password =body.get("password");

        User user = ur.findByEmailAndAuth(email, "local");

        if(user == null) {
            return new ResponseEntity<>("User with this email doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(!user.checkPassword(password)) {
            return new ResponseEntity<>("Invalid password", HttpStatus.FORBIDDEN);
        }

        Map<String, String> tokenMap = new HashMap<>();

        tokenMap.put("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
        tokenMap.put("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }

    @PostMapping("/auth0/login")
    public ResponseEntity<?> auth0Login(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email =body.get("email");

        User user = ur.findByEmailAndAuth(email, "auth0");

        if(user == null) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setAuth("auth0");
            user = ur.save(newUser);
        }

        Map<String, String> tokenMap = new HashMap<>();

        tokenMap.put("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
        tokenMap.put("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password =body.get("password");

        User existUser = ur.findByEmailAndAuth(email, "local");

        if(existUser == null) {
            User user = new User(name, email, password);
            user.setAuth("local");
            ur.save(user);

            Map<String, String> tokenMap = new HashMap<>();

            tokenMap.put("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
            tokenMap.put("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        }
    
        return new ResponseEntity<>("User with this email already exist", HttpStatus.FORBIDDEN);
    }
}
