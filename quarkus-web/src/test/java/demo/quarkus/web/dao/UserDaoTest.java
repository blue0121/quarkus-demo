package demo.quarkus.web.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import demo.quarkus.web.entity.User;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

/**
 * @author Jin Zheng
 * @since 2022-03-29
 */
@QuarkusTest
@TestTransaction
public class UserDaoTest {
    @Inject
    UserDao userDao;

    public UserDaoTest() {
    }

    @Test
    public void testSave() {
        User user = this.saveUser("test");

        var u2 = userDao.get(user.getId());
        Assertions.assertNotNull(u2);
        Assertions.assertEquals(user.getName(), u2.getName());
    }

    private User saveUser(String name) {
        User user = new User();
        user.setName(name);
        userDao.save(user);
        Assertions.assertNotNull(user.getId());

        Assertions.assertNotNull(userDao.get(user.getId()));
        var list = userDao.listAll();
        Assertions.assertEquals(1, list.size());

        return user;
    }

    @Test
    public void testUpdate() {
        User user = this.saveUser("test");

        user.setName("testtest");
        userDao.update(user);

        var u2 = userDao.get(user.getId());
        Assertions.assertNotNull(u2);
        Assertions.assertEquals("testtest", u2.getName());
    }

    @Test
    public void testDelete() {
        User user = this.saveUser("test");

        int rs = userDao.delete(user.getId());
        Assertions.assertEquals(1, rs);
    }

}
