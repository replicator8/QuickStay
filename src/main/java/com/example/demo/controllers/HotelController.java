package com.example.demo.controllers;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelController {
    private HotelService hotelService;

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels/{id}")
    Hotel getHotel(@PathVariable String id) {
        return hotelService.findById(id);
    }

    @GetMapping("/hotels/{id}/rating")
    Double getHotelRating(@PathVariable String id) {
        return hotelService.findById(id).getRating();
    }

    @GetMapping("/hotels/{id}/rooms")
    List<Room> getHotelRooms(@PathVariable String id) {
        return hotelService.getAllRooms(id);
    }
}
