package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetShoppingCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        List<Product> products = shoppingCartService.getByUserId(userId).getProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/shoppingcart.jsp").forward(req, resp);
    }
}
