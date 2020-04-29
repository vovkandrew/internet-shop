package internetshop.controllers;

import internetshop.lib.Inject;
import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductController extends HttpServlet {
    @Inject
    private static final Injector injector =
            Injector.getInstance("internetshop");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addproduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        productService.create(new Product(name, Double.valueOf(price)));
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }

}
