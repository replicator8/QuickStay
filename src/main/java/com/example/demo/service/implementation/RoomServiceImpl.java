package com.example.demo.service.implementation;

import com.example.demo.domain.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoomService;
import com.example.quickstay_contracts.input.CustomBookingForm;
import com.example.quickstay_contracts.viewmodel.RoomViewModelCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Room findById(String uuid) {
        return roomRepository.findById(uuid).get();
    }

    @Override
    public void setDiscount(String uuid, int percentageNumber) {
        Room room = findById(uuid);
        room.setPrice(room.getPrice() * (1 - (percentageNumber / 100.0)));
        roomRepository.save(room);
    }

    @Override
    public Page<RoomViewModelCustom> getCustomRooms(CustomBookingForm model) {
        Pageable pageable = PageRequest.of(model.page() - 1, model.size());
        int totalDays = (int) ChronoUnit.DAYS.between(model.start(), model.end());
        LocalDate startDate = model.start();
        LocalDate endDate = model.end();

        // get all rooms which suits by price
        Page<Room> roomsSuitByPrice = roomRepository.getRoomsByPrice(totalDays, model.price(), pageable);

        // check this rooms to be not booked for dates
        List<Room> availableRooms = roomsSuitByPrice.stream()
                .filter(room -> bookingRepository.checkAvailabilityForDates(room.getId(), startDate, endDate))
                .collect(Collectors.toList());

        // map to view model
        List<RoomViewModelCustom> rooms = new ArrayList<>();

        for (Room room: availableRooms) {
            rooms.add(new RoomViewModelCustom(
                    room.getId(),
                    room.getHotel().getName(),
                    room.getRoomType().getRus(),
                    room.getHotel().getAddress().getFullAddress(),
                    room.getPrice() * totalDays,
                    room.getPhoto()
                    ));
        }
        return new PageImpl<>(rooms, pageable, roomsSuitByPrice.getTotalElements());
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
