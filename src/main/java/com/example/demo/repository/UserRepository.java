package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u.balance from users u where u.id = :uuid")
    double getBalanceByUserId(@Param(value = "uuid") String uuid);
    @Query("select u.firstName, u.lastName from users u where u.id = :uuid")
    String getFirstNameAndLastNameByUserId(@Param(value = "uuid") String uuid);
    @Query("select u.age from users u where u.id = :uuid")
    int getAgeByUserId(@Param(value = "uuid") String uuid);
}
