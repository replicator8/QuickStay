package com.example.demo.dtos;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.User;
import java.time.LocalDate;

public class BookingDto {
    private String id;
    private Hotel hotel;
    private User user;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double price;
    private int duration;

    public BookingDto(String id, Hotel hotel, User user, double price, LocalDate dateStart, LocalDate dateEnd, int duration) {
        this.id = id;
        this.hotel = hotel;
        this.user = user;
        this.price = price;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
