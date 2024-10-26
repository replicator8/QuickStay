package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {
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

    @OneToOne
    public Address getAddress() {
        return address;
    }

    public String getAllAddress() {
        return address.getCountry() + ", " + address.getCity() + ", " + address.getStreet() + " " + address.getHouse();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

   @OneToMany(mappedBy = "hotel")
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
