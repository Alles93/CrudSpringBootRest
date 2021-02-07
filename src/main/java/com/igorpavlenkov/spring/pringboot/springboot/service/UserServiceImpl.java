package com.igorpavlenkov.spring.pringboot.springboot.service;

import com.igorpavlenkov.spring.pringboot.springboot.dao.UserDao;
import com.igorpavlenkov.spring.pringboot.springboot.model.AuthenticationProvider;
import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        if (!user.getPassword().startsWith("$")){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public void createNewUserAfterOAuthLoginSuccess(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Transactional
    @Override
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }


    public void createNewUserAfterOAuthLoginSuccess(String userName, String lastName, String email, AuthenticationProvider provider) {
        User user = new User();
        user.setUsername(userName);
        user.setAge(999);
        user.setEmail(email);
        user.setLastname(lastName);
        user.setPassword("***");
        user.setAuthenticationProvider(provider);
        userDao.saveUser(user);
    }

    public void updateNewUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider google) {
        user.setUsername(name);
        user.setAuthenticationProvider(google);

        userDao.updateUser(user);
    }
}
