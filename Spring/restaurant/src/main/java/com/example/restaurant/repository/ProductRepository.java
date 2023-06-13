package com.example.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
