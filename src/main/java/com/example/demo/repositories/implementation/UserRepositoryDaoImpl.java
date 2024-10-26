package com.example.demo.repositories.implementation;

import com.example.demo.domain.User;
import com.example.demo.repositories.GenericRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryDaoImpl extends GenericRepository<User, String> implements UserRepository {

    public UserRepositoryDaoImpl(Class<User> userClass) { super(userClass); }

    @Override
    public User findById(String uuid) {
        return entityManager
                .createQuery("select u from user u where u.id = :uuid", User.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }

    @Override
    public double getBalance(String uuid) {
        return entityManager.find(User.class, uuid).getBalance();
    }
}
