package internetshop.service;

import internetshop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    Optional<User> get(Long userId);

    List<User> getAll();

    User update(User user);

    boolean delete(Long userId);
}
