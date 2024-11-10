package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private int age;
    private double balance;

    protected User() {}

    public User(String firstName, String lastName, int age, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.balance = balance;
    }

    @Column(name = "firstName", nullable = false, length = 127)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastName", nullable = false, length = 127)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "balance", nullable = false)
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }
}
