package com.example.demo.dtos;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.User;
import java.time.LocalDate;

public class BookingDto {
    private String id;
    private Hotel hotel;
    private User user;
    private LocalDate date;
    private double price;
    private int duration;

    public BookingDto(String id, Hotel hotel, User user, double price, LocalDate date, int duration) {
        this.id = id;
        this.hotel = hotel;
        this.user = user;
        this.price = price;
        this.date = date;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
