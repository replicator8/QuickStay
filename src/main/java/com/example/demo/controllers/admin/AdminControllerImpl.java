package com.example.demo.controllers.admin;

import com.example.demo.service.BookingService;
import com.example.demo.service.HotelService;
import com.example.quickstay_contracts.controllers.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {
    private BookingService bookingService;
    private HotelService hotelService;

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @GetMapping("/getActiveBookings/{hotelUUID}")
    public String getAllBookingsForHotel(String hotelUUID) {
        return "";
    }

    @Override
    @GetMapping("/getArchiveBookings/{hotelUUID}")
    public String getAllBookingsForHotelArchive(String hotelUUID) {
        return "";
    }

    @Override
    @PutMapping("/cancelBooking/{bookingUUID}")
    public void cancelBooking(String bookingUUID) {
        bookingService.deleteBooking(bookingUUID);
    }
}
