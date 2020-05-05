package internetshop.web.filter;

import internetshop.lib.Injector;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private static final String USER_ID = "user_id";
    private Map<String, Set<Role.RoleName>> urlMappring = new HashMap<>();
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        urlMappring.put("/addproduct", Set.of(Role.RoleName.ADMIN));
        urlMappring.put("/products/alladmin", Set.of(Role.RoleName.ADMIN));
        urlMappring.put("/users/all", Set.of(Role.RoleName.ADMIN));
        urlMappring.put("/shoppingcart", Set.of(Role.RoleName.USER));
        urlMappring.put("/orders/all", Set.of(Role.RoleName.USER));
        urlMappring.put("/products/all", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getServletPath();
        if (urlMappring.get(url) == null) {
            chain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);
        if (isAuthorized(user, urlMappring.get(url))) {
            chain.doFilter(req, resp);
            return;
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessdenied.jsp").forward(req, resp);
            return;
        }
    }

    private boolean isAuthorized(User user, Set<Role.RoleName> roleNames) {
        for (Role.RoleName roleName: roleNames) {
            for (Role userRole: user.getRoles()) {
                if (roleName.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
