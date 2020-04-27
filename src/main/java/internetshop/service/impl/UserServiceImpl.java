package internetshop.service.impl;

import internetshop.dao.UserDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.User;
import internetshop.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
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
        return userDao.update(user);
    }

    @Override
    public void delete(Long userId) {
        userDao.delete(userId);
    }
}
