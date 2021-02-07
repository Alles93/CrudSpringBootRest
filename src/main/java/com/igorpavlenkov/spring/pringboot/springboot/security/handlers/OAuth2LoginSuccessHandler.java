package com.igorpavlenkov.spring.pringboot.springboot.security.handlers;

import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import com.igorpavlenkov.spring.pringboot.springboot.service.RoleServiceImpl;
import com.igorpavlenkov.spring.pringboot.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.igorpavlenkov.spring.pringboot.springboot.model.AuthenticationProvider.GOOGLE;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String name = oAuth2User.getName();
        String email = (String) oAuth2User.getAttributes().get("email");
        String lastName = (String) oAuth2User.getAttributes().get("family_name");
        System.out.println(oAuth2User.getAttributes());

        User user = userService.getUserByName(name);

        if (user == null) {
            userService.createNewUserAfterOAuthLoginSuccess(name, lastName, email, GOOGLE);
        } else {
            userService.updateNewUserAfterOAuthLoginSuccess(user, name, GOOGLE);
        }
        response.sendRedirect("/user");

        System.out.println("Name: " + name);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
