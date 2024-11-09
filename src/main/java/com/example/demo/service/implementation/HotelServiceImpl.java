package com.example.demo.service.implementation;

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

}
