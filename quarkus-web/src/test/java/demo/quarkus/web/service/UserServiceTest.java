package demo.quarkus.web.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import demo.quarkus.web.dao.UserDao;
import demo.quarkus.web.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
@QuarkusTest
public class UserServiceTest {
    @InjectMock
    UserDao userDao;

    @Inject
    UserService userService;

    public UserServiceTest() {
    }

    @Test
    public void testSave() {
        var user = this.create(null, "test");
        userService.save(user);
        Mockito.verify(userDao).save(Mockito.any());
        Mockito.verify(userDao, Mockito.never()).update(Mockito.any());
    }

    @Test
    public void testUpdate() {
        var user = this.create(1, "test");
        userService.save(user);
        Mockito.verify(userDao, Mockito.never()).save(Mockito.any());
        Mockito.verify(userDao).update(Mockito.any());
    }

    private User create(Integer id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

}
