package com.example.demo.repositories.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.repositories.BookingRepository;
import com.example.demo.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepositoryDaoImpl extends GenericRepository<Booking, String> implements BookingRepository {
    public BookingRepositoryDaoImpl() {
        super(Booking.class);
    }
}
