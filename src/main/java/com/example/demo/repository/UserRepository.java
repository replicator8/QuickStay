package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u.balance from user u where u.id = :uuid")
    double getBalanceByUserId(String uuid);
    @Query("select u.firstName, u.lastName from user u where u.id = :uuid")
    String getFirstNameAndLastNameByUserId(String uuid);
    @Query("select u.age from user u where u.id = :uuid")
    int getAgeByUserId(String uuid);
}
