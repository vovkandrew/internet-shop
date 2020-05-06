package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.model.User;
import internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class LogoutController extends HttpServlet {
    private static Logger logger = Logger.getLogger(LoginController.class);
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR =
            Injector.getInstance("internetshop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = userService.get((Long) req.getSession().getAttribute(USER_ID));
        logger.info(String.format(
                "User %s ID %s logged out",
                user.getName(),
                String.valueOf(user.getId())));
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
