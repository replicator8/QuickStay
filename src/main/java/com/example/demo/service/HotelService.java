package com.example.demo.service;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    Hotel findById(String uuid);
    List<Room> getAllFreeRooms(LocalDate start, LocalDate end);
    double getRating(String uuid);
    void addRating(String uuid, double userRating);
}
