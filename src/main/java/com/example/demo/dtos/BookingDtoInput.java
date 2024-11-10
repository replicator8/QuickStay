package com.example.demo.dtos;

import java.time.LocalDate;

public class BookingDtoInput {
    private String roomId;
    private String userId;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public BookingDtoInput(String roomId, String userId, LocalDate dateStart, LocalDate dateEnd) {
        this.roomId = roomId;
        this.userId = userId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String hotelId) {
        this.roomId = hotelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
