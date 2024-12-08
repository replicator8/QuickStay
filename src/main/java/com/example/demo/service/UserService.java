package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.demo.domain.User;
import com.example.quickstay_contracts.input.BookingCreateInputModel;
import com.example.quickstay_contracts.viewmodel.UserActiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserArchiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserRegisterForm;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User findByUserName(String userName);
    User findById(String uuid);
    List<User> findAll();
    double getBalanceById(String uuid);
    BookingCreateInputModel createBooking(BookingCreateInputModel bookingDto);
    boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate);
    List<Booking> getUserBookings(String userId);
    List<UserActiveBookingsViewModel> getActiveBookings(String userUUID);
    List<UserArchiveBookingsViewModel> getArchiveBookings(String userUUID);
    void createUser(UserRegisterForm userModel);
}
