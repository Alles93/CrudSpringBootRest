package com.igorpavlenkov.spring.pringboot.springboot.security.service;

import com.igorpavlenkov.spring.pringboot.springboot.dao.UserDao;
import com.igorpavlenkov.spring.pringboot.springboot.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Load user by username:" + " " + username);
        User user = userDao.getUserByName(username);
        if (user == null) {
            try {
                throw new Exception("User '" + username + "' not found ");
            } catch (Exception e) {
                throw new BadCredentialsException(e.getLocalizedMessage(), e);
            }
        }
        return user;
    }
}
