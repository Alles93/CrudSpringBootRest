package com.igorpavlenkov.spring.pringboot.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping(value = "/")
    public String indexx() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {

        return "loginPage";
    }

}
