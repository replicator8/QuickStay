package com.example.demo.service;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.quickstay_contracts.viewmodel.RoomBookingForm;
import com.example.quickstay_contracts.viewmodel.RoomViewModel;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    Hotel findByName(String name);
    Hotel findById(String uuid);
    List<Room> getAllRooms(String hotelId);
    List<Room> getAllFreeRooms(String hotelId, LocalDate date);
    Page<RoomViewModel> getAllFreeRoomsByDates(RoomBookingForm model);
    double getRating(String uuid);
    void updateRating(String uuid, double userRating);
    List<Hotel> findAll();
}
