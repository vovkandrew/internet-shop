package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.model.Role;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User admin = new User("admin", "1");
        admin.setRoles(Set.of(Role.of("ADMIN")));
        userService.create(admin);
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart userCart = new ShoppingCart(userService.get(userId));
        shoppingCartService.create(userCart);
        req.getRequestDispatcher("/WEB-INF/views/injectdata.jsp").forward(req, resp);

    }
}
