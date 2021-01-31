package com.igorpavlenkov.spring.pringboot.springboot.dao;

import com.igorpavlenkov.spring.pringboot.springboot.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteRoleById(Long id) {
        Role role = getRoleById(id);
        entityManager.remove(role);
        System.out.println("Пользователь удален " + role);
    }
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }


    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery("select u from Role u where u.name=:name")
                .setParameter("name", name)
                .getSingleResult();
    }
}
