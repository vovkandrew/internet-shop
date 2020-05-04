package internetshop.security;

import internetshop.exception.AuthenticationException;
import internetshop.model.User;

public interface AuthenticationService {
    User getByLogin(String login, String password) throws AuthenticationException;
}
