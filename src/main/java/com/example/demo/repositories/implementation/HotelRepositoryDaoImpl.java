package com.example.demo.repositories.implementation;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.repositories.GenericRepository;
import com.example.demo.repositories.HotelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class HotelRepositoryDaoImpl extends GenericRepository<Hotel, String> implements HotelRepository {

    private final ModelMapper modelMapper = new ModelMapper();

    public HotelRepositoryDaoImpl(Class<Hotel> hotelClass) {
        super(hotelClass);
    }

    @Override
    public String findByName(String name) {
        return entityManager.find(Hotel.class, name).getName();
    }

    @Override
    public String findByAddress(String name) {
        return entityManager.find(Hotel.class, name).getAllAddress();
    }

    @Override
    public double getRating() {
        return 0;
    }

    @Override
    public List<Room> getAllRooms() {
        return List.of();
    }

    @Override
    public Room getRoomById(String uuid) {
        return null;
    }
}
