package com.igorpavlenkov.spring.pringboot.springboot.service;


import com.igorpavlenkov.spring.pringboot.springboot.dao.RoleDao;
import com.igorpavlenkov.spring.pringboot.springboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;


    @Transactional(readOnly = true)
    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Transactional
    @Override
    public void deleteRoleById(Long id) {
        roleDao.deleteRoleById(id);
    }
}
