package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.quickstay_contracts.viewmodel.BookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingViewModelFilter;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;

import java.util.List;

public interface BookingService {
    Booking findById(String uuid);
    List<Booking> getAllBookings();
    boolean deleteBooking(String bookingUUID);
    List<HotelViewModel> getHotels(BookingViewModel model);
    List<HotelViewModel> getHotelsWithFilter(BookingViewModelFilter model);
}
