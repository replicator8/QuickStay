package com.example.demo.controllers;

import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.PriceController;
import com.example.quickstay_contracts.input.BookingCreateInputModel;
import com.example.quickstay_contracts.input.CustomBookingInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceControllerImpl implements PriceController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/getRooms")
    public String getRooms(@RequestBody CustomBookingInputModel customBookingInputModel) {
        // TODO: ~

        return "";
    }

    @Override
    @PostMapping("/createBooking")
    public void createBooking(@RequestBody BookingCreateInputModel model) {
        userService.createBooking(model);
    }
}
