package com.example.demo.service;

import com.example.demo.domain.Booking;
import com.example.demo.domain.User;
import com.example.quickstay_contracts.input.BookingPriceForm;
import com.example.quickstay_contracts.input.UserBookingsForm;
import com.example.quickstay_contracts.viewmodel.UserActiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserArchiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserRegisterForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User findByUserName(String userName);
    User findById(String uuid);
    List<User> findAll();
    double getBalanceById(String uuid);
    BookingPriceForm createBooking(BookingPriceForm bookingDto);
    boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate);
    Page<Booking> getUserBookings(String userId, Pageable pageable);
    Page<UserActiveBookingsViewModel> getActiveBookings(UserBookingsForm model);
    Page<UserArchiveBookingsViewModel> getArchiveBookings(UserBookingsForm model);
    void createUser(UserRegisterForm userModel);
}
