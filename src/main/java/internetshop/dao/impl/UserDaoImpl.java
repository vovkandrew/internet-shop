package internetshop.dao.impl;

import internetshop.dao.UserDao;
import internetshop.dao.Storage;
import internetshop.lib.Dao;
import internetshop.model.User;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.addUser(user);
        return user;
    }

    @Override
    public User get(Long id) {
        return Storage.users
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User update(User user) {
        IntStream.range(0, Storage.users.size())
                .filter(number -> user.getId().equals(Storage.users.get(number).getId()))
                .forEach(number -> Storage.users.set(number, user));
        return user;
    }

    @Override
    public void delete(Long id) {
        Storage.users.removeIf(user -> user.getId().equals(id));
    }
}
