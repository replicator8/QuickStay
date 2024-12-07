package com.example.demo.domain;

import jakarta.persistence.*;
import java.net.URL;

@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {
    private String name;
    private String description;
    private Double rating;
    private Address address;
    private int roomsCount;
    private URL photo;
    private int ratingCount;

    protected Hotel() {}

    public Hotel(String name, Double rating, Address address, int roomsCount, URL photo, String description) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.roomsCount = roomsCount;
        this.photo = photo;
        this.address = address;
        this.ratingCount = 1;
    }

    @Column(name = "name", nullable = false, length = 127)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setAddress(Address address) {
        this.address = address;
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

    @Column(name = "rating_count")
    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", address=" + address +
                ", roomsCount=" + roomsCount +
                ", photo=" + photo +
                ", ratingCount=" + ratingCount +
                '}';
    }
}
