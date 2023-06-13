package filters;

import java.io.IOException;
import java.util.Arrays;

import Models.User;
import Models.enums.Role;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AccessFilter implements Filter {
    private Role[] accessRoles = new Role[] { Role.ADMIN };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if(req.getAttribute("user") != null) {
            User user = (User) req.getAttribute("user");

            Role role = user.getRole();

            if(!Arrays.asList(this.accessRoles).contains(role) ) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "User does not have access");
                return;
            }
        }
        else {
            // Arrays.stream().filter();
        }
    
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
