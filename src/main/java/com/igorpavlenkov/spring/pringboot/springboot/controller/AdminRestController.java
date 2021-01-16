package com.igorpavlenkov.spring.pringboot.springboot.controller;

import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import com.igorpavlenkov.spring.pringboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public User getUser(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }


    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.getUserById(id);
        userService.deleteUser(id);
    }


    @PostMapping()
    public User addUser(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.addUser(user);
        return user;
    }

    @PutMapping("{id}")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }
}
