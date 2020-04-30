package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductFromCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        shoppingCartService.deleteProduct(shoppingCartService.getByUserId(USER_ID),
                productService.get(Long.valueOf(req.getParameter("id"))));
        shoppingCartService.update(shoppingCartService.getByUserId(USER_ID));
        resp.sendRedirect(req.getContextPath() + "/shoppingcart");
    }
}
