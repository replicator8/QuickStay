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

    public HotelRepositoryDaoImpl() {
        super(Hotel.class);
    }

    @Override
    public Hotel findById(String uuid) {
        return entityManager.find(Hotel.class, uuid);
    }

    @Override
    public String findByName(String name) {
        return entityManager.find(Hotel.class, name).getName();
    }

    @Override
    public String findByAddress(String name) {
        return entityManager.find(Hotel.class, name).getFullAddress();
    }

    @Override
    public double getRating(String id) {
        return entityManager.find(Hotel.class, id).getRating();
    }

    @Override
    public List<Room> getAllRooms(String id) {
        return List.of();
    }

    @Override
    public Room getRoomById(String uuid) {
        return null;
    }
}
