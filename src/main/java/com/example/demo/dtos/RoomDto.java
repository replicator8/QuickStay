package com.example.demo.dtos;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.enums.RoomStatus;
import com.example.demo.domain.enums.RoomType;
import java.net.URL;

public class RoomDto {
    private String id;
    private Hotel hotel;
    private RoomStatus roomStatus;
    private RoomType roomType;
    private double price;
    private URL photo;

    public RoomDto(String id, Hotel hotel, RoomStatus roomStatus, RoomType roomType, double price, URL photo) {
        this.id = id;
        this.hotel = hotel;
        this.roomStatus = roomStatus;
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

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
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
}
