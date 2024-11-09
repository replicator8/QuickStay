package com.example.demo.repository.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepositoryDaoImpl extends GenericRepository<Booking, String> implements BookingRepository {
    public BookingRepositoryDaoImpl() {
        super(Booking.class);
    }
}
