package com.example.demo.domain;

import com.example.demo.constants.RoomType;
import jakarta.persistence.*;
import java.net.URL;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    private Hotel hotel;
    private String description;
    private RoomType roomType;
    private double price;
    private URL photo;

    protected Room() {}

    public Room(Hotel hotel, String description, RoomType roomType, double price, URL photo) {
        this.hotel = hotel;
        this.description = description;
        this.roomType = roomType;
        this.price = price;
        this.photo = photo;
    }

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Room{" +
                "hotel=" + hotel +
                ", description='" + description + '\'' +
                ", roomType=" + roomType +
                ", price=" + price +
                ", photo=" + photo +
                '}';
    }
}
