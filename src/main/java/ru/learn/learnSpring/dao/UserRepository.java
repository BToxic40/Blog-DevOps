package ru.learn.learnSpring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.learn.learnSpring.model.User;

public class UserRepository {

    @Autowired
    SessionFactory sessionFactory;

    public User getUserById(int id) {
        return getCurrentSession().get(User.class, id);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
