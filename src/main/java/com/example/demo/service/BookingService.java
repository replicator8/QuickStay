package com.example.demo.service;

import com.example.demo.domain.Booking;
import java.util.List;

public interface BookingService {
    Booking findById(String uuid);
    List<Booking> getAllBookings();
}
