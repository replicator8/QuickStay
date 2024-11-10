package com.example.demo.dtos;

import java.time.LocalDate;

public class BookingDtoOutput extends BookingDtoInput {
    private String id;

    public BookingDtoOutput(String roomId, String userId, LocalDate dateStart, LocalDate dateEnd) {
        super(roomId, userId, dateStart, dateEnd);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
