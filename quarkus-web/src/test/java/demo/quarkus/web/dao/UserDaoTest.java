package demo.quarkus.web.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import demo.quarkus.web.entity.User;
import io.quarkus.test.junit.QuarkusTest;

/**
 * @author Jin Zheng
 * @since 2022-03-29
 */
@QuarkusTest
@Transactional
public class UserDaoTest {
    @Inject
    EntityManager entityManager;

    @Inject
    UserDao userDao;

    public UserDaoTest() {
    }

    @BeforeEach
    public void beforeEach() {
        entityManager.createQuery("delete from User").executeUpdate();
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
