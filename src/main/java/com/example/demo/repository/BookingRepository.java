package com.example.demo.repository;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("select b.room from bookings b where b.id = :uuid")
    Room getRoomByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.user from bookings b where b.id = :uuid")
    User getUserByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.date_start from bookings b where b.id = :uuid")
    User getDateByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.price from bookings b where b.id = :uuid")
    double getPriceByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.duration from bookings b where b.id = :uuid")
    User getDurationByBookingId(@Param(value = "uuid") String uuid);
}
