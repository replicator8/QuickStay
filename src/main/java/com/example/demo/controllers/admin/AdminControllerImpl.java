package com.example.demo.controllers.admin;

import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.controllers.AdminController;
import com.example.quickstay_contracts.viewmodel.AdminBookingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {
    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // MARK: ok
    @Override
    @GetMapping("/getAdminAvailableBookings/{hotelName}")
    public List<AdminBookingViewModel> getAllBookingsForHotel(@PathVariable String hotelName) {
        return bookingService.getAdminBookingsForHotelName(hotelName);
    }
}
