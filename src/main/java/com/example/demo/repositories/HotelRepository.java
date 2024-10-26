package com.example.demo.repositories;

import com.example.demo.domain.Room;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository {
    String findByName(String name);
    String findByAddress(String name);
    double getRating();
    List<Room> getAllRooms();
    Room getRoomById(String uuid);
}
