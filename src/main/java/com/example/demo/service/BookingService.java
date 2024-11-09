package com.example.demo.service;

import com.example.demo.domain.Booking;

public interface BookingService {
    Booking findById(String uuid);

}
