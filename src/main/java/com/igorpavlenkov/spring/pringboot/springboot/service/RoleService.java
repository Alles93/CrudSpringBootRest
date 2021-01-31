package com.igorpavlenkov.spring.pringboot.springboot.service;

import com.igorpavlenkov.spring.pringboot.springboot.model.Role;

public interface RoleService {

    Role getRoleByName(String name);

    void deleteRoleById(Long id);

}
