package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.quickstay_contracts.input.AdminBookingForm;
import com.example.quickstay_contracts.viewmodel.AdminBookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingForm;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.data.domain.Page;

public interface BookingService {
    Booking findById(String uuid);
    boolean deleteBooking(String bookingUUID);
    Page<HotelViewModel> getHotels(BookingForm model);
    Page<HotelViewModel> getHotelsWithFilter(BookingForm model);
    Page<AdminBookingViewModel> getAdminBookingsForHotelName(AdminBookingForm model);
}
