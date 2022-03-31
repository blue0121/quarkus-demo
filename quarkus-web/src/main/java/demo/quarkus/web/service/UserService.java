package demo.quarkus.web.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import demo.quarkus.web.dao.UserDao;
import demo.quarkus.web.entity.User;

/**
 * @author Jin Zheng
 * @since 2022-03-29
 */
@ApplicationScoped
@Transactional
public class UserService {
    @Inject
    UserDao userDao;

    public UserService() {
    }

    public User get(Integer id) {
        return userDao.get(id);
    }

    public List<User> listAll() {
        return userDao.listAll();
    }

    public User save(User user) {
        if (user.getId() == null) {
            userDao.save(user);
        } else {
            userDao.update(user);
        }
        return userDao.get(user.getId());
    }

    public int delete(Integer id) {
        return userDao.delete(id);
    }
}
