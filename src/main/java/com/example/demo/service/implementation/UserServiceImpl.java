package com.example.demo.service.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Room;
import com.example.demo.domain.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository, HotelRepository hotelRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public User findById(String uuid) {
        return userRepository.findById(uuid).get();
    }

    @Override
    public double getBalanceById(String uuid) {
        return findById(uuid).getBalance();
    }

    @Override
    public boolean createBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate) {
        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i < totalDays; i++) {
            if (!bookingRepository.checkAvailability(roomId, startDate.plusDays(i))) return false;
        }

        Room room = roomRepository.findById(roomId).get();
        User user = userRepository.findById(userId).get();
        double totalPrice = roomRepository.getPriceByRoomId(roomId) * totalDays;
        Booking booking = new Booking(room, user, startDate, endDate, totalPrice, totalDays);

        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = getUserBookings(userId);
        for (Booking booking: bookings) {
            if (Objects.equals(booking.getRoom().getId(), roomId) && booking.getDateStart() == startDate && booking.getDateEnd() == endDate) {
                bookingRepository.delete(booking);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.getUserBookings(userId);
    }
}
