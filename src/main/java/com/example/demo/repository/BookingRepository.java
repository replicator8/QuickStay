package com.example.demo.repository;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Room;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query("select b.room from Booking b where b.id = :uuid")
    Room getRoomByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.user from Booking b where b.id = :uuid")
    User getUserByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.dateStart from Booking b where b.id = :uuid")
    LocalDate getStartDateByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.dateEnd from Booking b where b.id = :uuid")
    LocalDate getEndDateByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.price from Booking b where b.id = :uuid")
    double getPriceByBookingId(@Param(value = "uuid") String uuid);
    @Query("select b.duration from Booking b where b.id = :uuid")
    int getDurationByBookingId(@Param(value = "uuid") String uuid);
    @Query("select case when count(b) > 0 THEN false ELSE true END from Booking b " +
            "where b.room.id = :roomId and :checkDate BETWEEN b.dateStart AND b.dateEnd")
    boolean checkAvailability(@Param("roomId") String roomId, @Param("checkDate") LocalDate checkDate);
    @Query("select case when count(b) > 0 THEN false ELSE true END from Booking b where b.room.id = :roomId and ((:endDate > b.dateStart and :startDate < b.dateEnd))")
    boolean checkAvailabilityForDates(@Param("roomId") String roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("select b from Booking b where b.user.id = :userId")
    List<Booking> getUserBookings(@Param(value = "userId") String userId);
    @Query("select b from Booking b")
    List<Booking> getAllBookings();
    @Query("select b from Booking b join Room r on b.room.id = r.id where r.hotel.name = :hotelName")
    List<Booking> getHotelBookingsByName(@Param(value = "hotelName") String hotelName);
}
