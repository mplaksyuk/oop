package Controllers;

import abstr.ControllerAbstract;
import filters.AccessFilter;
import filters.AuthFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import Dao.OrderDao;
import Dao.UserDao;
import Models.Order;
import Models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSONObject;

@WebServlet({"/users", "/users/*"})
public class UserController extends ControllerAbstract {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        processRequest(req, resp);

        getMapping("", (m, r) -> { // /users
            List<User> users = UserDao.getUsers();

            String json = JSONObject.toJsonString(users);

            out.println(json);
        },
        new AuthFilter(),
        new AccessFilter()
        );

        getMapping("/current", (m, r) -> { // /users/current
            User user = (User) req.getAttribute("user");

            String json = JSONObject.toJsonString(user);

            out.println(json);
        },
        new AuthFilter()
        );

        getMapping("/(\\d+)/orders", (m, r) -> { // /users/{id}/orders
            List<Order> orders = OrderDao.getOrders(Integer.valueOf(m.get(0)));
            
            String json = JSONObject.toJsonString(orders);

            out.println(json);
        });

        getMapping("/(\\d+)/orders/(\\d+)", (m, r) -> { // /users/{id}/orders/{id}
            Integer user_id = Integer.valueOf(m.get(0));
            Integer order_id = Integer.valueOf(m.get(1));

            Order order = OrderDao.getOrderById(user_id, order_id);
            
            String json = JSONObject.toJsonString(order);

            out.println(json);
        });

        responseError();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        processRequest(req, resp);

        getMapping("/(\\d+)/info/(\\w+)/(\\d+)", (m, r) -> { // /users/id
            out.println(m);

            out.println(r.get("name"));
            out.println(r.get("id"));

        });
    }
}
