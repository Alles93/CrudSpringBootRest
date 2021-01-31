package com.igorpavlenkov.spring.pringboot.springboot.controller;

import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import com.igorpavlenkov.spring.pringboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rest/users")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }


    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }


    @PostMapping()
    public User createUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PutMapping(value = "{id}")
    public User editUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.getUserById(id);
        userService.updateUser(user);
        return user;
    }
}
