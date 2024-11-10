package com.example.demo.controllers;

import com.example.demo.dtos.BookingDtoInput;
import com.example.demo.service.BookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
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

    @PostMapping("/newBooking")
    public BookingDtoInput createBooking(@RequestBody BookingDtoInput bookingDto) {
        return userService.createBooking(bookingDto);
    }

}
