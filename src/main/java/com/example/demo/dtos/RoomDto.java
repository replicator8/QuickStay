package com.example.demo.dtos;

import com.example.demo.domain.Hotel;
import com.example.demo.constants.RoomType;
import java.net.URL;

public class RoomDto {
    private String id;
    private Hotel hotel;
    private String description;
    private RoomType roomType;
    private double price;
    private URL photo;

    public RoomDto(String id, Hotel hotel, String description, RoomType roomType, double price, URL photo) {
        this.id = id;
        this.hotel = hotel;
        this.description = description;
        this.roomType = roomType;
        this.price = price;
        this.photo = photo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
