package com.example.restaurant.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.entity.User;
import com.example.restaurant.tokenManager.TokenManager;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/tokens")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class TokenController {

    @PostMapping("/refresh")
    public ResponseEntity<?> login(@RequestHeader Map<String, String> headers) {
        String refreshHeader = headers.get("refresh");
        String refreshToken;

        if (refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Invalid token", HttpStatus.FORBIDDEN);
        }

        refreshToken = refreshHeader.split(" ")[1];

        if (!TokenManager.verifyRefreshToken(refreshToken)) {
            return new ResponseEntity<>("Invalid token", HttpStatus.FORBIDDEN);
        }

        User user = TokenManager.parseRefreshToken(refreshToken);
        TokenManager.removeRefreshToken(refreshToken);

        Map<String, String> tokenMap = new HashMap<>();

        tokenMap.put("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
        tokenMap.put("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}
