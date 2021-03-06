package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.User;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    private final SessionFactory factory;

    @Autowired
    public UserDaoImpl(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    @Override
    public User add(User user) {
        user = super.add(user);
        return user;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        try (Session session = factory.openSession()) {
            Query<User> query = session
                    .createQuery("from User u "
                            + "left join fetch u.roles Role "
                            + " where u.email =: email", User.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find user with email "
                    + email, e);
        }
    }

    @Override
    public User getById(Long userId) {
        return super.getById(userId, User.class);
    }
}
