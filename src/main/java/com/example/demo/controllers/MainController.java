package com.example.demo.controllers;

import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private HotelService hotelService;

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel1")
    String getHotel() {
        return hotelService.findByName("Four Seasons Hotel Moscow").getName();
    }
}
