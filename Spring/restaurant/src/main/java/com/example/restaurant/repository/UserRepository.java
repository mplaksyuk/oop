package com.example.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndAuth(String email, String auth);
    User findByEmail(String email);
}
