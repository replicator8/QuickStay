package com.example.demo.repositories.implementation;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    String findByFirstNameAndLastName(String firstName, String lastName);
    double getBalance(String uuid);
}
