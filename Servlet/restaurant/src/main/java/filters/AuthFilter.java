package filters;

import java.io.IOException;

import Dao.UserDao;
import Models.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import tokenManager.TokenManager;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String authorizationHeader = req.getHeader("Authorization");
        String token;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        token = authorizationHeader.split(" ")[1];

        if (!TokenManager.verifyAccessToken(token)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        User user = TokenManager.parseAccessToken(token);

        user = UserDao.getUser(user.getId());

        req.setAttribute("user", user);
    
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
