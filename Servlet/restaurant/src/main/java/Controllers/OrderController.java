package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Dao.OrderDao;
import Models.Order;
import Models.Product;
import Models.User;
import abstr.ControllerAbstract;
import filters.AccessFilter;
import filters.AuthFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSONObject;

@WebServlet({"/orders", "/orders/*"})
public class OrderController extends ControllerAbstract {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        processRequest(req, resp);

        getMapping("", (m, r) -> {
            User user = (User) req.getAttribute("user");

            List<Order> orders = user.getRole().name() == "ADMIN" ? 
                OrderDao.getOrders() : OrderDao.getOrders(user.getId());
            String json = JSONObject.toJsonString(orders);
            out.println(json);
        },
        new AuthFilter()
        );

        getMapping("/(\\d+)", (m, r) -> { // /orders/id
            Integer order_id = Integer.valueOf(m.get(0));
            Order order = OrderDao.getOrderById(order_id);
            Order newOrder = new Order();
            newOrder.copy(order);
            String json = JSONObject.toJsonString(newOrder);
            out.println(json);
        });

        responseError();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        processRequest(req, resp);

        getMapping("/(\\d+)/confirm", (m, r) -> {
            Integer order_id = Integer.valueOf(m.get(0));

            Order order = OrderDao.confirmOrder(order_id);

            out.println(JSONObject.toJsonString(order));
        },
        new AuthFilter(),
        new AccessFilter()
        );

        getMapping("/(\\d+)/paid", (m, r) -> {
            Integer order_id = Integer.valueOf(m.get(0));
            User user = (User) req.getAttribute("user");

            Order order = OrderDao.paidOrder(order_id, user.getId());

            out.println(JSONObject.toJsonString(order));
        },
        new AuthFilter()
        );

        getMapping("/add", (m, r) -> {
            List<Product> products = new ArrayList<>();

            JsonArray products_array = r.getAsJsonArray("products");

            for (JsonElement productElement : products_array) {
                JsonObject product = productElement.getAsJsonObject();
                Integer product_id = product.get("id").getAsInt();
                String product_name = product.get("name").getAsString();

                products.add(new Product(product_id, product_name));
            }

            User user = (User) req.getAttribute("user");

            Order order = new Order();
            order.setUser(user);
            order.setProducts(products);


            Order newOrder = OrderDao.addOrder(order);

            out.println(JSONObject.toJsonString(newOrder));
        },
        new AuthFilter()
        );

        responseError();
    }
}
