package com.example.demo.repository;

import com.example.demo.constants.RoomStatus;
import com.example.demo.constants.RoomType;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.URL;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Query("select r.hotel from room r where r.id = :uuid")
    Hotel getHotelByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.status from room r where r.id = :uuid")
    RoomStatus getStatusByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.description from room r where r.id = :uuid")
    String getDescriptionByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.type from room r where r.id = :uuid")
    RoomType getTypeByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.price from room r where r.id = :uuid")
    double getPriceByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.photo from room r where r.id = :uuid")
    URL getPhotoByRoomId(@Param(value = "uuid") String uuid);
}
