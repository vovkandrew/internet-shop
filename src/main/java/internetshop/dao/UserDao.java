package internetshop.dao;

import internetshop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    User create(User user);

    Optional<User> get(Long userId);

    List<User> getAll();

    User update(User user);

    void delete(Long userId);
}
