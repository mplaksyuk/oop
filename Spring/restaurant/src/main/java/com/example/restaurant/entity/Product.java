package com.example.restaurant.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "product", schema="public")
public class Product {

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", updatable = true, nullable = true)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "image", updatable = true, nullable = true)
    private String image;

    @Column(name = "weight", updatable = true, nullable = true)
    @NotNull(message = "Weight is required")
    private Integer weight;

    @Column(name = "price", updatable = true, nullable = true)
    @NotNull(message = "Price is required")
    private Integer price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "product_ingredient", 
        joinColumns = { @JoinColumn(name = "product_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}