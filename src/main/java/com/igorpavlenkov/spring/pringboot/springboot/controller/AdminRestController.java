package com.igorpavlenkov.spring.pringboot.springboot.controller;

import com.igorpavlenkov.spring.pringboot.springboot.model.Role;
import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import com.igorpavlenkov.spring.pringboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/rest/users")public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin/rest/users")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> users = userService.getAllUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/user/rest/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
       final User user= userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/rest/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping(value = "/admin/new")
    public ResponseEntity<?> create(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "lastname") String lastname,
                                    @RequestParam(value = "age") int age,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "password") String password,
                                    @RequestParam("role") String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleByName(roles));
        }
        userService.updateUser(new User(username, lastname, age, email, password, roleSet));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/admin/editSave")
    public ResponseEntity<?> editUser(Model model,
                                      @RequestParam("id") Long id,
                                      @RequestParam(value = "username") String username,
                                      @RequestParam(value = "lastname") String lastname,
                                      @RequestParam(value = "age") int age,
                                      @RequestParam(value = "email") String email,
                                      @RequestParam(value = "password") String password,
                                      @RequestParam("role") String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleByName(roles));
        }
        userService.updateUser(new User(id, username, lastname, age, email, password, roleSet));
        return new ResponseEntity<>(HttpStatus.OK);

    }


}

