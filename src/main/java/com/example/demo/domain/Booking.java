package com.example.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    private Room room;
    private User user;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private double price;
    private int duration;

    protected Booking() {}

    public Booking(Room room, User user, LocalDate dateStart, LocalDate dateEnd, double price, int duration) {
        this.room = room;
        this.user = user;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.price = price;
        this.duration = duration;
    }

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "date_start", nullable = false)
    public LocalDate getDate() {
        return dateStart;
    }

    public void setDate(LocalDate date) {
        this.dateStart = date;
    }

    @Column(name = "date_end", nullable = false)
    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
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
