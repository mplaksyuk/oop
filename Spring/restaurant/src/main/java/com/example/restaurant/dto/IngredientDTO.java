package com.example.restaurant.dto;

import com.example.restaurant.entity.Ingredient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientDTO {
    private int id;
    private String name;

    public static IngredientDTO Convert(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        return dto;
    }
}