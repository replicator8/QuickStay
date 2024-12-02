package com.example.demo.controllers;

import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.BookingController;
import com.example.quickstay_contracts.viewmodel.BookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingViewModelFilter;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingControllerImpl implements BookingController {
    private UserService userService;
    private BookingService bookingService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @PostMapping("/getHotels")
    public String getHotels(@RequestBody BookingViewModel bookingViewModel) {
        // TODO: add to page
        List<HotelViewModel> hotels = bookingService.getHotels(bookingViewModel);

        return "Booking";
    }

    @Override
    @PostMapping("/getHotelsWithFilter")
    public String getHotelsWithFilter(@RequestBody BookingViewModelFilter bookingViewModelFilter) {
        // TODO: ~

        return "Booking";
    }
}
