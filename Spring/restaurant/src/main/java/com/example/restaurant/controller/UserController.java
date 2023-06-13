package com.example.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.restaurant.dto.UserDTO;
import com.example.restaurant.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository ur;

    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity<>(ur.findAll().stream().map(UserDTO::Convert).toArray(), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer user_id = Integer.parseInt(authentication.getName());
        // String role = authentication.getAuthorities().iterator().next().getAuthority().substring(5);
        return new ResponseEntity<>(ur.findById(user_id).map(UserDTO::Convert).orElse(null), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        return new ResponseEntity<>(ur.findById(id).map(UserDTO::Convert).orElse(null), HttpStatus.OK);
    }
}
