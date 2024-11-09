package com.example.demo.repository;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    List<Hotel> findByName(String name);
    List<Hotel> findByAddress(String address);
    @Query("select h.rating from hotels h where h.id = :uuid")
    double getRatingByHotelId(@Param(value = "uuid") String uuid);
    @Query("select h.rooms_count from hotels h where h.id = :uuid")
    int getRoomsCountByHotelId(@Param(value = "uuid") String uuid);
    @Query("select h.photo from hotels h where h.id = :uuid")
    URL getPhotoByHotelId(@Param(value = "uuid") String uuid);
    @Query(value = "select r from rooms r where r.hotel.id=:uuid")
    List<Room> getAllRooms(@Param(value = "uuid") String uuid);
}
