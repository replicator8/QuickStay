package com.example.demo.services.implementation;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.repositories.implementation.HotelRepositoryDaoImpl;
import com.example.demo.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepositoryDaoImpl hotelRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Hotel findById(String uuid) {
        return hotelRepository.findById(uuid);
    }

    @Override
    public double getRating(String uuid) {
        return hotelRepository.getRating(uuid);
    }

    @Override
    public Room findRoomById(String uuid) {
        return null;
    }

    @Override
    public List<Room> getAllFreeRooms(LocalDate start, LocalDate end) {
        return List.of();
    }

    @Override
    public void addRating(double userRating) {

    }
}
