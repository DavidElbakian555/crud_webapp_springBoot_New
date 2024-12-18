package com.elbakian.webapp.crud_webapp_sprongboot.dao;

import com.elbakian.webapp.crud_webapp_sprongboot.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserDaoImpl {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<User> showUsers(){
        return em.createQuery("from User").getResultList();
    }

    @Transactional
    public void saveUser(User user){
        Role defaultRole = new Role("ROLE_USER");
        defaultRole.setUser(user);
        user.setRoles(new ArrayList<>(Collections.singletonList(defaultRole)));
        em.persist(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(int id){
        return em.find(User.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username){
        User user = (User) em.createQuery("from User where email = :username")
                .setParameter("username", username).getSingleResult();
        return Optional.ofNullable(user);
    }


    @Transactional
    public void updateUser(User user, int id){
        User changedUser = getUserById(id);
        changedUser.setName(user.getName());
        changedUser.setLastName(user.getLastName());
        changedUser.setEmail(user.getEmail());
        em.merge(changedUser);
    }

    @Transactional
    public void deleteUser(int id){
        User user = getUserById(id);
        user.getRoles().forEach(role -> em.remove(role));
        user.getRoles().clear();
        em.remove(user);
    }
}
