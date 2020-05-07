package internetshop.controller;

import internetshop.exception.AuthenticationException;
import internetshop.lib.Injector;
import internetshop.model.User;
import internetshop.security.AuthenticationService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {
    private static Logger logger = Logger.getLogger(LoginController.class);
    private static final Injector INJECTOR =
            Injector.getInstance("internetshop");
    private final AuthenticationService authenticationService =
            (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/loginpage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pwd = req.getParameter("pwd");
        try {
            User user = authenticationService.getByLogin(login, pwd);
            HttpSession session = req.getSession();
            session.setAttribute("user_id", user.getId());
            logger.info(String.format(
                    "User %s ID %s logged in",
                    user.getName(),
                    String.valueOf(user.getId())));

        } catch (AuthenticationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/loginpage.jsp").forward(req, resp);
            return;

        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
