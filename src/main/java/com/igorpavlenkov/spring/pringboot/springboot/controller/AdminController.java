
package com.igorpavlenkov.spring.pringboot.springboot.controller;

import com.igorpavlenkov.spring.pringboot.springboot.model.Role;
import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import com.igorpavlenkov.spring.pringboot.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    private User user;

    @GetMapping()
    public String getUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "newUser")
    public String getUser() {
        return "addUser";
    }


    @PostMapping(value = "new")
    public String addNewUser(@RequestParam(value = "username") String username,
                             @RequestParam(value = "lastname") String lastname,
                             @RequestParam(value = "age") int age,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "password") String password,
                             @RequestParam("role") String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(userService.getRoleByName(roles));
        }
        userService.updateUser(new User(username,lastname,age,email,password,roleSet));
        return "redirect:users";
    }


    @GetMapping("edit")
    public String editPage(@RequestParam("id") Long id, ModelMap model){
        model.addAttribute("user", userService.getUserById(id));
        return "editUser";
    }

    @PostMapping("editSave")
    public String editUser(Model model,
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
        userService.updateUser(new User(id,username,lastname,age,email,password,roleSet));
        return "redirect:users";
    }


    @DeleteMapping("delete")
    public String deleteUser(@RequestParam(value = "id") String id) {

        Long userId = Long.parseLong(id);
        userService.deleteUser(userId);
        return "redirect:users";
    }
}

