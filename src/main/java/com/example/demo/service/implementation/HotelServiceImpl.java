package com.example.demo.service.implementation;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.HotelService;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public Hotel findById(String uuid) {
        return hotelRepository.findById(uuid).get();
    }

    @Override
    public List<Room> getAllRooms(String hotelId) {
        return roomRepository.getRoomsByHotelId(hotelId);
    }

    @Override
    public List<Room> getAllFreeRooms(String hotelId, LocalDate date) {
        List<Room> hotelRooms = getAllRooms(hotelId);

        for(Room room: hotelRooms) {
            if (!bookingRepository.checkAvailability(room.getId(), date)) {
                hotelRooms.remove(room);
            }
        }
        return hotelRooms;
    }

    @Override
    public double getRating(String uuid) {
        return hotelRepository.getRatingByHotelId(uuid);
    }

    @Override
    public void updateRating(String uuid, double userRating) {
       if (userRating >= 0 && userRating <= 5) {
           Hotel hotel = hotelRepository.findById(uuid).get();
           double newRating = ((hotel.getRating() * hotel.getRatingCount()) + userRating) / (hotel.getRatingCount() + 1);
           hotelRepository.addUserRating(newRating, uuid);
           hotel.setRatingCount(hotel.getRatingCount() + 1);
       }
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }
}
