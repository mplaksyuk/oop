package Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.Ingredient;
import abstr.DaoAbstract;

public class IngredientDao extends DaoAbstract {
    public static List<Ingredient> getIngredients() {
        String query = "SELECT * FROM ingredient";

        List<Ingredient> ingredients = new ArrayList<>();

        connect(query, (statement) -> {
            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer id = rSet.getInt("id");
                String name = rSet.getString("name");

                ingredients.add(new Ingredient(id, name));
            }
        });
        return ingredients;
    }

    public static Ingredient getIngredient(Integer ingredient_id) {
        String query = "SELECT * FROM ingredient WHERE id = ?";

        Ingredient ingredient = new Ingredient();

        connect(query, (statement) -> {
            statement.setInt(1, ingredient_id);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                ingredient.setId( ingredient_id );
                ingredient.setName( rSet.getString("name") );
            }
        });

        return ingredient;
    }

    public static List<Ingredient> getIngredientsByProductId(Integer product_id) {
        String query = "SELECT * FROM ingredient WHERE id IN (SELECT ingredient_id FROM product_ingredient WHERE product_id = ?)";

        List<Ingredient> ingredients = new ArrayList<>();

        connect(query, (statement) -> {
            statement.setInt(1, product_id);

            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer id = rSet.getInt("id");
                String name = rSet.getString("name");

                ingredients.add(new Ingredient(id, name));
            }
        });

        return ingredients;
    }

    public static Ingredient addIngredient(Ingredient ingredient) {
        String query = "INSERT INTO ingredient (name) VALUES(?) RETURNING id";
        
        Ingredient newIngredient = new Ingredient();

        connect(query, (statement) -> {
            statement.setString(1, ingredient.getName());
            ResultSet rSet = statement.executeQuery();
            if(rSet.next()) {
                Integer id = rSet.getInt(1);
                String name = ingredient.getName();

                newIngredient.setId(id);
                newIngredient.setName(name);
            }

        });

        return newIngredient;
    }
}
