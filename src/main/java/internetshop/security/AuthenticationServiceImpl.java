package internetshop.security;

import internetshop.exception.AuthenticationException;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User getByLogin(String login, String password) throws AuthenticationException {
        User user = userService.getByLogin(login)
                .orElseThrow(() -> new AuthenticationException("Wrong login or password"));
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new AuthenticationException("Wrong login or password");
    }
}
