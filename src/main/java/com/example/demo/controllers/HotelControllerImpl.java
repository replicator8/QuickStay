package com.example.demo.controllers;

import com.example.demo.domain.Hotel;
import com.example.demo.service.HotelService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.HotelController;
import com.example.quickstay_contracts.input.BookingCreateInputModel;
import com.example.quickstay_contracts.viewmodel.RoomBookingModel;
import com.example.quickstay_contracts.viewmodel.RoomBookingModelFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelControllerImpl implements HotelController {
    private HotelService hotelService;
    private UserService userService;

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getHotelRooms(RoomBookingModel roomBookingModel) {
        // TODO: ~

        return "";
    }

    @PostMapping("/getRoomsWithFilter")
    public String getHotelRoomsWithFilter(@RequestBody RoomBookingModelFilter roomBookingModelFilter) {

        return "Hotel";
    }

    @Override
    @PostMapping("/createBooking")
    public void createBooking(@RequestBody BookingCreateInputModel model) {
        userService.createBooking(model);
    }

    @GetMapping("/hotels/{id}")
    Hotel getHotel(@PathVariable String id) {
        return hotelService.findById(id);
    }

    @GetMapping("/hotels/{id}/rating")
    Double getHotelRating(@PathVariable String id) {
        return hotelService.findById(id).getRating();
    }

}
