package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import Dao.IngredientDao;
import Models.Ingredient;
import abstr.ControllerAbstract;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSONObject;

@WebServlet({"/ingredients/*"})
public class IngredientController extends ControllerAbstract {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        processRequest(req, resp);

        getMapping("", (m, r) -> {
            List<Ingredient> ingredients = IngredientDao.getIngredients();

            out.println(JSONObject.toJsonString(ingredients));
        });

        responseError();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalStateException  {
        PrintWriter out = resp.getWriter();
        processRequest(req, resp);

        getMapping("/add", (m, r) -> {

            String name = r.get("name").getAsString();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(name);

            Ingredient newIngredient = IngredientDao.addIngredient(ingredient);
            
            out.println(JSONObject.toJsonString(newIngredient));
        });

        responseError();
    }
}
