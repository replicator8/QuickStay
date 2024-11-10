package com.example.demo;

import com.example.demo.repository.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Locale;
import java.util.Random;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    BookingRepository bookingRepository;

    Faker fakerRus = new Faker(new Locale("ru"));
    Faker fakerUSA = new Faker(new Locale("en-US"));
    Random rand = new Random();

    public MyCommandLineRunner(HotelRepository hotelRepository, AddressRepository addressRepository, UserRepository userRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 50; i++) {

        }

    }
}
