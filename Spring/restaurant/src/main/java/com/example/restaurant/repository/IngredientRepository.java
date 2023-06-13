package com.example.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    
}
