package com.example.demo.repository;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository {
    Hotel findById(String uuid);
    String findByName(String name);
    String findByAddress(String name);
    double getRating(String id);
    List<Room> getAllRooms(String id);
    Room getRoomById(String uuid);
}
