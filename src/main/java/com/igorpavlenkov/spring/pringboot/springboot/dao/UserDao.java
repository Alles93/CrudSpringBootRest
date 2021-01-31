package com.igorpavlenkov.spring.pringboot.springboot.dao;


import com.igorpavlenkov.spring.pringboot.springboot.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByName(String username);

    void updateUser(User user);

    void saveUser(User user);

    void deleteUserById(Long id);


}
