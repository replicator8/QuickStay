package com.example.demo.domain;

import jakarta.persistence.*;
import java.net.URL;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {
    private String name;
    private Double rating;
    private Address address;
    private int roomsCount;
    private URL photo;
    private List<Room> roomList;
    private int ratingCount;

    protected Hotel() {}

    public Hotel(String name, Double rating, Address address, int roomsCount, URL photo, List<Room> roomList) {
        this.name = name;
        this.rating = rating;
        this.roomsCount = roomsCount;
        this.photo = photo;
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
        ratingCount++;
        this.rating += rating;
        this.rating /= ratingCount;
    }

    @OneToOne
    public Address getAddress() {
        return address;
    }

    @Transient
    public String getFullAddress() {
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

    @Column(name = "photo", nullable = false)
    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
        this.photo = photo;
    }

    @Column(name = "rooms_count", nullable = false)
    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    @Transient
    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
