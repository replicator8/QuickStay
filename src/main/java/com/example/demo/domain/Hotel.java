package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "address")
public class Hotel {
    private String name;
    private Double rating;
    private Address address;
    private List<Room> roomList;

    protected Hotel() {}

    public Hotel(String name, Double rating, Address address, List<Room> roomList) {
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.roomList = roomList;
    }

    @Column(name = "name", nullable = false, unique = true, length = 127)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "rating", nullable = false)
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    // TODO: check
    @OneToOne
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

   // TODO: Add a relation
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
