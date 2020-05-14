package internetshop.dao.impl;

import internetshop.dao.UserDao;
import internetshop.db.Storage;
import internetshop.model.User;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public Optional<User> get(Long userId) {
        return Optional.of(Storage.users
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(number -> user.getId().equals(Storage.users.get(number).getId()))
                .forEach(number -> Storage.users.set(number, user));
        return user;
    }

    @Override
    public void delete(Long userId) {
        Storage.users.removeIf(user -> user.getId().equals(userId));
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Storage.users.stream().filter(user -> user.getName().equals(login)).findFirst();
    }
}
