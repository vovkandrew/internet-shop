package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.model.User;
import internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String username = req.getParameter("username");
        String password = req.getParameter("pwd");
        String passwordRepeat = req.getParameter("pwdRep");
        if (password.equals(passwordRepeat)) {
            userService.create(new User(username));
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message",
                    "Your password and repeated password missmatch");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }

}
