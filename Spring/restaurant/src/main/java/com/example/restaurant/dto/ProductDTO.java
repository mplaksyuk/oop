package com.example.restaurant.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.restaurant.entity.Product;
import com.example.restaurant.entity.Ingredient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private String image;
    private Integer weight;
    private Integer price;
    private List<Ingredient> ingredients = new ArrayList<>();

    public static ProductDTO Convert(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setImage(product.getImage());
        dto.setWeight(product.getWeight());
        dto.setPrice(product.getPrice());
        dto.setIngredients(product.getIngredients());
        return dto;
    }

    public static Product Entity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setImage(dto.getImage());
        product.setWeight(dto.getWeight());
        product.setPrice(dto.getPrice());
        product.setIngredients(dto.getIngredients());
        return product;
    }
}
