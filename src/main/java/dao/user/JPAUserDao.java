package dao.user;

import dto.Subject;
import dto.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 09.10.2015.
 */
@JPAUser
@Stateless
public class JPAUserDao implements UserDAO{
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public JPAUserDao() {
    }

    public JPAUserDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if(!entityManager.contains(user))
            user = entityManager.merge(user);
        return user;
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createNamedQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public boolean deleteUser(User user) {
        user = updateUser(user);
        entityManager.remove(user);
        for(Subject subject : user.getSubjects()){
            subject.getUsers().remove(user);
        }
        return true;
    }

    public void close(){
        entityManager.close();
    }
}
