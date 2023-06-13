package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.Ingredient;
import Models.Product;
import abstr.DaoAbstract;

public class ProductDao extends DaoAbstract {

    public static List<Product> getProducts() {
        String query = "SELECT * FROM product";

        List<Product> products = new ArrayList<>();

        connect(query, (statement) -> {
            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer id    = rSet.getInt("id");
                String  name  = rSet.getString("name");
                String  image  = rSet.getString("image");
                Integer  price = rSet.getInt("price");
                Integer weight = rSet.getInt("weight");

                List<Ingredient> ingredients = IngredientDao.getIngredientsByProductId(id);

                products.add(new Product(id, name, image, ingredients, weight, price));
            }
        });

        return products;
    }

    public static Product getProduct(Integer product_id) {
        String query = "SELECT * FROM product WHERE id = ?";

        Product product = new Product();

        connect(query, (statement) -> {
            statement.setInt(1, product_id);

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                List<Ingredient> ingredients = IngredientDao.getIngredientsByProductId(product_id);

                product.setId(product_id);
                product.setName( rSet.getString("name") );
                product.setImage( rSet.getString("image"));
                product.setIngredients(ingredients);
            }
        });

        return product;
    }

    public static List<Product> getProductsByOrderId(Integer order_id) {
        String query = "SELECT p.* FROM product p INNER JOIN order_product op ON p.id = op.product_id WHERE op.order_id = ?";

        List<Product> products = new ArrayList<>();

        connect(query, (statement) -> {
            statement.setInt(1, order_id);

            ResultSet rSet = statement.executeQuery();

            while(rSet.next()) {
                Integer product_id = rSet.getInt("id");
                String name = rSet.getString("name");
                String image = rSet.getString("image");
                Integer price = rSet.getInt("price");
                Integer weight = rSet.getInt("weight");
                List<Ingredient> ingredients = IngredientDao.getIngredientsByProductId(product_id);
                products.add(new Product(product_id, name, image, ingredients, weight, price));

            }
        });

        return products;
    }

    public static Product addProduct(Product product) {
        String query = "INSERT INTO product (name, image, price, weight) VALUES(?, ?, ?, ?) RETURNING id";

        Product newProduct = new Product();

        connect(query, (connection, statement) -> {
            statement.setString(1, product.getName());
            statement.setString(2, product.getImage());
            statement.setInt(3, product.getPrice());
            statement.setInt(4, product.getWeight());

            ResultSet rSet = statement.executeQuery();

            if(rSet.next()) {
                Integer product_id = rSet.getInt(1);

                String query_ = "INSERT INTO product_ingredient VALUES(?, ?)";

                PreparedStatement statement_ = connection.prepareStatement(query_);

                for(Ingredient ingredient : product.getIngredients()) {
                    statement_.setInt(1, product_id);
                    statement_.setInt(2, ingredient.getId());

                    statement_.addBatch();
                }
                statement_.executeBatch();

                connection.commit();
                
                newProduct.copy(getProduct(product_id));
            }
        });

        return newProduct;
    }
}