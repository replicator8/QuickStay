package com.example.demo.domain;

import com.example.demo.domain.enums.RoomStatus;
import com.example.demo.domain.enums.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import java.net.URL;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    private Hotel hotel;
    private RoomStatus roomStatus;
    private RoomType roomType;
    private double price;
    private URL photo;

    protected Room() {}

    public Room(Hotel hotel, RoomStatus roomStatus, RoomType roomType, double price, URL photo) {
        this.hotel = hotel;
        this.roomStatus = roomStatus;
        this.roomType = roomType;
        this.price = price;
        this.photo = photo;
    }

    // TODO: add relation
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Column(name = "status", nullable = false, length = 31)
    @Enumerated(EnumType.STRING)
    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Column(name = "type", nullable = false, length = 31)
    @Enumerated(EnumType.STRING)
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "photo", nullable = false)
    public URL getPhoto() {
        return photo;
    }

    public void setPhoto(URL photo) {
        this.photo = photo;
    }
}
