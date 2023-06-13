package Controllers;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

import Models.User;
import abstr.ControllerAbstract;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tokenManager.TokenManager;

@WebServlet({"/tokens/*"})
public class TokenController extends ControllerAbstract {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalStateException  {
        PrintWriter out = resp.getWriter();
        processRequest(req, resp);

        getMapping("/refresh", (m, r) -> {

            String refreshHeader = req.getHeader("Refresh");
            String refreshToken;

            if (refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
                responseError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            refreshToken = refreshHeader.split(" ")[1];

            if (!TokenManager.verifyRefreshToken(refreshToken)) {
                responseError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

            User user = TokenManager.parseRefreshToken(refreshToken);
            TokenManager.removeRefreshToken(refreshToken);

            JsonObject tokens = new JsonObject();

            tokens.addProperty("accessToken", TokenManager.generateAccessToken(user.getId(), user.getRole()));
            tokens.addProperty("refreshToken", TokenManager.generateRefreshToken(user.getId(), user.getRole()));

            out.println(tokens);
        });

        responseError();
    }
}
