package demo.quarkus.web.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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
    EntityManager entityManager;

    @Inject
    UserDao userDao;

    public UserDaoTest() {
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("test");
        userDao.save(user);
        Assertions.assertNotNull(user.getId());

        var u2 = userDao.get(user.getId());
        Assertions.assertNotNull(u2);
        Assertions.assertEquals(user.getName(), u2.getName());
    }

}
