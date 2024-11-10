package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.demo.domain.User;
import com.example.demo.dtos.BookingDtoInput;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User findById(String uuid);
    List<User> findAll();
    double getBalanceById(String uuid);
    BookingDtoInput createBooking(BookingDtoInput bookingDto);
    boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate);
    List<Booking> getUserBookings(String userId);
}
