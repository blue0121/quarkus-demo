package demo.quarkus.web.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import demo.quarkus.web.entity.User;

/**
 * @author Jin Zheng
 * @since 2022-03-29
 */
@ApplicationScoped
public class UserDao {
    @Inject
    EntityManager em;

    public UserDao() {
    }

    public User get(Integer id) {
        return em.find(User.class, id);
    }

    public List<User> listAll() {
        var query = em.createQuery("from User", User.class);
        return query.getResultList();
    }

    public void save(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(User user) {
        em.remove(user);
    }

}
