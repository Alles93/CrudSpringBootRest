package com.igorpavlenkov.spring.pringboot.springboot.dao;

import com.igorpavlenkov.spring.pringboot.springboot.model.Role;

public interface RoleDao {
    Role getRoleByName (String name);
    Role getRoleById(Long id);
    void deleteRoleById(Long id);
}
