package Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Product {
    private Integer id;
    private String name, image;
    private List<Ingredient> ingredients;
    private Integer weight;
    private Integer price;

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(String name, List<Ingredient> ingredients, Integer price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    public void copy(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.ingredients = product.getIngredients();
        this.price = product.getPrice();
    }
}
