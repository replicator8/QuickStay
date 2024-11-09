package com.example.demo.service.implementation;

import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    public RoomServiceImpl(UserRepository userRepository, RoomRepository roomRepository, HotelRepository hotelRepository, BookingRepository bookingRepository) {
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

}
