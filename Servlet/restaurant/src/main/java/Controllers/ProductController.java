package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Dao.ProductDao;
import Models.Ingredient;
import Models.Product;
import abstr.ControllerAbstract;
import filters.AccessFilter;
import filters.AuthFilter;
import filters.InputValidatorFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSONObject;

@WebServlet({"/products/*"})
public class ProductController extends ControllerAbstract {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        processRequest(req, resp);

        getMapping("", (m, r) -> {
            List<Product> products = ProductDao.getProducts();
            
            out.println(JSONObject.toJsonString(products));
        });

        getMapping("/(\\d+)", (m, r) -> { //products/{id}
            Integer product_id = Integer.valueOf(m.get(0));
            Product product = ProductDao.getProduct(product_id);

            out.println(JSONObject.toJsonString(product));
        }, 
        new AuthFilter()
        );

        responseError();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalStateException  {
        PrintWriter out = resp.getWriter();
        processRequest(req, resp);

        getMapping("/add", (m, r) -> {
            Product product = new Product();

            String name = r.get("name").getAsString();
            String image = r.get("image").getAsString();
            Integer weight = r.get("weight").getAsInt();
            Integer price = r.get("price").getAsInt();

            List<Ingredient> ingredients = new ArrayList<>();
            JsonArray ingredients_array = r.getAsJsonArray("ingredients");

            for (JsonElement ingredientElement : ingredients_array) {
                JsonObject ingredient = ingredientElement.getAsJsonObject();
                Integer ingredient_id = ingredient.get("id").getAsInt();
                String ingredient_name = ingredient.get("name").getAsString();

                ingredients.add(new Ingredient(ingredient_id, ingredient_name));
            }

            product.setName(name);
            product.setImage(image);
            product.setPrice(price);
            product.setWeight(weight);
            product.setIngredients(ingredients);

            Product newProduct = ProductDao.addProduct(product);

            out.println(JSONObject.toJsonString(product));
            out.println(JSONObject.toJsonString(newProduct));

        },
        new AuthFilter(),
        new AccessFilter(),
        new InputValidatorFilter("name", new String[] {"mandatory"} )
        );
    }
}
