package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.demo.domain.User;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User findById(String uuid);
    List<User> findAll();
    double getBalanceById(String uuid);
    boolean createBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate);
    boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate);
    List<Booking> getUserBookings(String userId);
}
