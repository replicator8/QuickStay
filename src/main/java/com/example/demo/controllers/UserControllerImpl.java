package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.service.BookingService;
import com.example.demo.service.HotelService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.UserController;
import com.example.quickstay_contracts.viewmodel.UserActiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserArchiveBookingsViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private UserService userService;
    private HotelService hotelService;
    private BookingService bookingService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @GetMapping("/getActiveBookings/{userUUID}")
    public String getActiveBookings(@PathVariable String userUUID) {
        List<UserActiveBookingsViewModel> bookings = userService.getActiveBookings(userUUID);
        // TODO: display bookings

        return "User profile";
    }

    @Override
    @GetMapping("/getArchiveBookings/{userUUID}")
    public String getArchiveBookings(@PathVariable String userUUID) {
        List<UserArchiveBookingsViewModel> bookings = userService.getArchiveBookings(userUUID);
        // TODO: display bookings

        return "User profile";
    }

    @Override
    @DeleteMapping("/cancelBooking/{bookingUUID}")
    public void cancelBooking(@PathVariable String bookingUUID) {
        bookingService.deleteBooking(bookingUUID);
    }

    @Override
    @PutMapping("/rateBooking/{bookingUUID}/{rating}")
    public void rateBooking(@PathVariable String bookingUUID, @PathVariable double rating) {
        String hotelUUID = bookingService.findById(bookingUUID).getRoom().getHotel().getId();
        hotelService.updateRating(hotelUUID, rating);
    }

    @GetMapping("/getAll")
    List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable String id) {
        return userService.findById(id);
    }
}
