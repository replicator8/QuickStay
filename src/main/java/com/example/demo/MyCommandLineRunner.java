package com.example.demo;

import com.example.demo.domain.Address;
import com.example.demo.domain.Hotel;
import com.example.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.net.URL;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private HotelRepository hotelRepository;

    public MyCommandLineRunner(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Address address1 = new Address("Russia", "Moscow", "Okhotny Ryad St.", "2");
        Hotel hotel1 = new Hotel(
                "Four Seasons Hotel",
                3.0,
                address1,
                300,
                new URL("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/11/5d/59/86/exterior--v17631841.jpg?w=1200&amp;h=-1&amp;s=1"));

        hotelRepository.save(hotel1);
    }
}
