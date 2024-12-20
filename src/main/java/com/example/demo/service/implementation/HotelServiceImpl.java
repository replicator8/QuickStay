package com.example.demo.service.implementation;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.HotelService;
import com.example.quickstay_contracts.viewmodel.RoomBookingForm;
import com.example.quickstay_contracts.viewmodel.RoomViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    private final ModelMapper modelMapper = new ModelMapper();

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
    public Page<RoomViewModel> getAllFreeRoomsByDates(RoomBookingForm model) {
        Pageable pageable = PageRequest.of(model.page() - 1, model.size());
        String hotelId = model.hotelUUID();
        LocalDate startDate = model.start();
        LocalDate endDate = model.end();
        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate);

        // get all hotel rooms
        Page<Room> allRooms = hotelRepository.getAllRooms(hotelId, pageable);

        // check if room is available & map
        List<RoomViewModel> availableRooms = allRooms.getContent().stream()
                .filter(room -> bookingRepository.checkAvailabilityForDates(room.getId(), startDate, endDate))
                .map(room -> new RoomViewModel(
                        room.getId(),
                        room.getRoomType().getRus(),
                        room.getDescription(),
                        Double.parseDouble(String.format("%.2f", room.getPrice() * totalDays)),
                        room.getPhoto()
                ))
                .toList();

        return new PageImpl<>(availableRooms, pageable, allRooms.getTotalElements());
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
