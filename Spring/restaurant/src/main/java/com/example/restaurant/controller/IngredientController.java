package com.example.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restaurant.entity.Ingredient;
import com.example.restaurant.dto.IngredientDTO;
import com.example.restaurant.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/ingredients")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class IngredientController {

    @Autowired
    private final IngredientRepository ir;

    @GetMapping("")
    public ResponseEntity<?> getAllIngredients() {
        return new ResponseEntity<>(ir.findAll().stream().map(IngredientDTO::Convert).toArray(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredient(@PathVariable Integer id) {
        return new ResponseEntity<>(ir.findById(id).map(IngredientDTO::Convert).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addIngredient(@RequestBody String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ir.save(ingredient);

        return new ResponseEntity<>(IngredientDTO.Convert(ingredient), HttpStatus.OK);
    }   
}