package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    private Hotel hotel;
    private User user;
    private LocalDate date;
    private double price;
    private int duration;

    protected Booking() {}

    public Booking(Hotel hotel, User user, LocalDate date, double price, int duration) {
        this.hotel = hotel;
        this.user = user;
        this.date = date;
        this.price = price;
        this.duration = duration;
    }

    // TODO: relation
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    // TODO: add relation
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "date", nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
