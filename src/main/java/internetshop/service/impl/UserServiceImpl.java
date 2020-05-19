package internetshop.service.impl;

import static internetshop.util.password.PasswordUtil.getSalt;
import static internetshop.util.password.PasswordUtil.hashPassword;

import internetshop.dao.UserDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    @Override
    public User create(User user) {
        User newUser = new User(user.getName());
        byte[] newSalt = getSalt();
        newUser.setSalt(newSalt);
        String hashedPassword = hashPassword(user.getPassword(), newSalt);
        newUser.setPassword(hashedPassword);
        return userDao.create(newUser);
    }

    @Override
    public User get(Long userId) {
        return userDao.get(userId).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        User newUser = new User(user.getName());
        byte[] newSalt = getSalt();
        newUser.setSalt(newSalt);
        String hashedPassword = hashPassword(user.getPassword(), newSalt);
        newUser.setPassword(hashedPassword);
        return userDao.update(newUser);
    }

    @Override
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userDao.getByLogin(login);
    }
}
