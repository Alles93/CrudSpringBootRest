package com.igorpavlenkov.spring.pringboot.springboot.dao;


import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
        System.out.println("Пользователь обновлен!");
    }

    @Override
    public void saveUser(User user) {
        entityManager.merge(user);
        System.out.println("Пользователь создан!");
    }

    @Override
    public void deleteUserById(Long id) {
        entityManager.createQuery("delete from User p where p.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public User getUserByName(String username) {
        try {
            User user = (User) entityManager.createQuery("select u from User u where u.username=:username")
                    .setParameter("username", username)
                    .getSingleResult();
            return user;
        } catch (NoResultException ex) {
            return null;
        }
    }

}