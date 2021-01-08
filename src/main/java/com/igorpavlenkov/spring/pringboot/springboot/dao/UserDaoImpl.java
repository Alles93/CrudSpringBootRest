package com.igorpavlenkov.spring.pringboot.springboot.dao;


import com.igorpavlenkov.spring.pringboot.springboot.model.Role;
import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;


    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        return entityManager.unwrap(Session.class).createQuery("from User").getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.unwrap(Session.class).createQuery("from User where id = '" + id + "'", User.class).getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        entityManager.unwrap(Session.class).saveOrUpdate(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        entityManager.unwrap(Session.class).delete(user);
    }

    @Override
    public User getUserByName(String username) {
        return entityManager.unwrap(Session.class).createQuery("from User where username = '" + username + "'", User.class).getSingleResult();
    }

    @Override
    public Role getRoleByName(String name) {

        return entityManager.unwrap(Session.class).createQuery("from Role where name = '" + name + "'", Role.class).getSingleResult();
    }

    @Override
    public void addRole(Role role) {
        entityManager.unwrap(Session.class).save(role);
    }

}