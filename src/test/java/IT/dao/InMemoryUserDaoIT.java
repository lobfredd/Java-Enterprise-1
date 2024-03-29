package IT.dao;

import no.westerdals.lobfre13.lms.dao.user.InMemoryUserDao;
import no.westerdals.lobfre13.lms.dto.User;
import no.westerdals.lobfre13.lms.dao.user.UserDAO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class InMemoryUserDaoIT {
    private UserDAO db;

    @Before
    public void setUp() throws Exception {
        db = new InMemoryUserDao();
    }

    @Test
    public void addUser(){
        assertTrue(db.addUser(new User()).getId() > 0);
    }

    @Test
    public void getUser(){
        db.addUser(new User());
        db.addUser(new User());
        db.addUser(new User());
        db.addUser(new User());
        assertTrue(db.getUser(1) != null);
        assertTrue(db.getUser(1).getId() == 1);
    }

    @Test
    public void updateUser(){
        User user = new User();
        db.addUser(user);
        user.setEmail("mail");
        assertTrue(db.updateUser(user).getEmail().equals("mail"));
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        db.addUser(user);
        assertTrue(db.deleteUser(user));
    }

    @Test
    public void getUsers() throws Exception {
        db.addUser(new User());
        db.addUser(new User());
        db.addUser(new User());
        assertTrue(db.getAllUsers().size() >= 3);
    }
}