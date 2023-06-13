package com.example.restaurant.controller;

import java.util.stream.Collectors;

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
import com.example.restaurant.entity.Product;
import com.example.restaurant.dto.ProductDTO;
import com.example.restaurant.repository.IngredientRepository;
import com.example.restaurant.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/products")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductRepository pr;

    @Autowired
    private final IngredientRepository ir;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(pr.findAll().stream().map(ProductDTO::Convert).toArray(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        return new ResponseEntity<>(pr.findById(id).map(ProductDTO::Convert).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getIngredient(@RequestBody ProductDTO product) {
        Product newProduct = ProductDTO.Entity(product);
        newProduct.setIngredients(ir.findAllById(product.getIngredients().stream()
                                    .map(Ingredient::getId)
                                    .collect(Collectors.toList())));
        pr.save(newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    } 
}