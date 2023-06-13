package Controllers;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import Dao.UserDao;
import Models.User;
import abstr.ControllerAbstract;
import filters.InputValidatorFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tokenManager.TokenManager;


@WebServlet({"/auth/*"})
public class AuthController extends ControllerAbstract {
    private static final Logger log = Logger.getLogger(AuthController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getMapping("", (m, r) -> {
        });

        responseError();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalStateException  {
        PrintWriter out = resp.getWriter();
        processRequest(req, resp);

        getMapping("/login", (m, r) -> {

            String email    = r.get("email").getAsString();
            String password = r.get("password").getAsString();

            User user = UserDao.getUserByEmailAndAuth(email, "local");

            if(user.getEmail()== null) {
                responseError(HttpServletResponse.SC_FORBIDDEN, "User with this email doesn't exist");
                return;
            }

            if(!user.checkPassword(password)) {
                responseError(HttpServletResponse.SC_FORBIDDEN, "Invalid password");
                return;
            }

            JsonObject tokens = new JsonObject();

            tokens.addProperty("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
            tokens.addProperty("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

            resp.addCookie(new Cookie("role", user.getRole().name()));

            out.println(tokens);
        },
        new InputValidatorFilter("email", new String[] {"mandatory", "isEmail"}),
        new InputValidatorFilter("password", new Integer[] {6, 12}, new String[] {"mandatory", "length"})
        );

        getMapping("/auth0/login", (m, r) -> {

            String name     = r.get("name").getAsString();
            String email    = r.get("email").getAsString();

            User user = UserDao.getUserByEmailAndAuth(email, "auth0");
            if (user.getEmail() == null) {
                User newUser = new User(name, email);
                newUser.setAuth("auth0");
                user.copy(UserDao.addUser(newUser));
            }

            JsonObject tokens = new JsonObject();

            tokens.addProperty("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
            tokens.addProperty("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

            resp.addCookie(new Cookie("role", user.getRole().name()));

            out.println(tokens);
        },
        new InputValidatorFilter("email", new String[] {"mandatory", "isEmail"})
        );

        getMapping("/register", (m, r) -> {
            String name     = r.get("name").getAsString();
            String email    = r.get("email").getAsString();
            String password = r.get("password").getAsString();

            User existUser = UserDao.getUserByEmail(email);

            if(existUser.getEmail() != null && existUser.getEmail().equals(email)) {
                responseError(HttpServletResponse.SC_FORBIDDEN, "User with this email already exist");
                return;
            }

            User user = new User(name, email, password);
            user.setAuth("local");

            User newUser = UserDao.addUser(user);

            JsonObject tokens = new JsonObject();

            tokens.addProperty("accessToken", TokenManager.generateAccessToken(newUser.getId(), newUser.getRole()));
            tokens.addProperty("refreshToken", TokenManager.generateRefreshToken(newUser.getId(), newUser.getRole()));

            resp.addCookie(new Cookie("role", newUser.getRole().name()));

            out.println(tokens);
        },
        new InputValidatorFilter("name", new Integer[] {2, 20},  new String[] {"mandatory", "length"} ),
        new InputValidatorFilter("email", new String[] {"mandatory", "isEmail"}),
        new InputValidatorFilter("password", new Integer[] {6, 12}, new String[] {"mandatory", "length"})
        );

        responseError();
    }
}
