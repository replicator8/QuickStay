package com.example.demo.controllers;

import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.controllers.BookingController;
import com.example.quickstay_contracts.viewmodel.BookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingViewModelFilter;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingControllerImpl implements BookingController {
    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // MARK: ok
    @Override
    @PostMapping("/getHotels")
    public List<HotelViewModel> getHotels(@RequestBody BookingViewModel bookingViewModel) {
        // TODO: add to page
        List<HotelViewModel> hotels = bookingService.getHotels(bookingViewModel);

        return hotels;
    }

    // MARK: ok
    @Override
    @PostMapping("/getHotelsWithFilter")
    public List<HotelViewModel> getHotelsWithFilter(@RequestBody BookingViewModelFilter filter) {
        // TODO: add to page
        List<HotelViewModel> hotels = bookingService.getHotelsWithFilter(filter);

        return hotels;
    }

}
