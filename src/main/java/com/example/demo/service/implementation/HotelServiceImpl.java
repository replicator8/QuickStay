package com.example.demo.service.implementation;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.repository.HotelRepository;
import com.example.demo.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Autowired
    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel findById(String uuid) {
        return hotelRepository.findById(uuid).get();
    }

    @Override
    public List<Room> getAllFreeRooms(LocalDate start, LocalDate end) {
        return List.of();
    }

    @Override
    public double getRating(String uuid) {
        return hotelRepository.getRatingByHotelId(uuid);
    }

    @Override
    public void addRating(String uuid, double userRating) {
       if (userRating >= 0 && userRating <= 5) {
           Hotel hotel = hotelRepository.findById(uuid).get();
           double newRating = ((hotel.getRating() * hotel.getRatingCount()) + userRating) / (hotel.getRatingCount() + 1);
           hotelRepository.addUserRating(newRating, uuid);
           hotel.setRatingCount(hotel.getRatingCount() + 1);
       }
    }
}
