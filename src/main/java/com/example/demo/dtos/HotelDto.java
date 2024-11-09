package com.example.demo.dtos;

import com.example.demo.domain.Address;
import java.net.URL;

public class HotelDto {
    private String id;
    private String name;
    private double rating;
    private Address address;
    private int roomsCount;
    private URL photo;

    public HotelDto(String id, String name, double rating, Address address, int roomsCount, URL photo) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.roomsCount = roomsCount;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
        this.photo = photo;
    }
}
