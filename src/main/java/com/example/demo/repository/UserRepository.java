package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.userName = :userName")
    User findByUserName(@Param(value = "userName") String userName);
    @Query("select u.balance from User u where u.id = :uuid")
    double getBalanceByUserId(@Param(value = "uuid") String uuid);
    @Query("select u.firstName from User u where u.id = :uuid")
    String getFirstNameByUserId(@Param(value = "uuid") String uuid);
    @Query("select u.lastName from User u where u.id = :uuid")
    String getLastNameByUserId(@Param(value = "uuid") String uuid);
    @Query("select u.age from User u where u.id = :uuid")
    int getAgeByUserId(@Param(value = "uuid") String uuid);
}
