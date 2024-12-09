package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.quickstay_contracts.viewmodel.AdminBookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingForm;
import com.example.quickstay_contracts.viewmodel.BookingFilterForm;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {
    Booking findById(String uuid);
    List<Booking> getAllBookings();
    boolean deleteBooking(String bookingUUID);
    Page<HotelViewModel> getHotels(BookingForm model);
    Page<HotelViewModel> getHotelsWithFilter(BookingForm model);
    List<AdminBookingViewModel> getAdminBookingsForHotelName(String hotelName);
}
