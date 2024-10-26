package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository {
    String findByName(String name);
}
