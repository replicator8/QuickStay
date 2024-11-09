package com.example.demo.repository;

import com.example.demo.constants.RoomType;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.net.URL;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    @Query("select r.hotel from Room r where r.id = :uuid")
    Hotel getHotelByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.description from Room r where r.id = :uuid")
    String getDescriptionByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.roomType from Room r where r.id = :uuid")
    RoomType getTypeByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.price from Room r where r.id = :uuid")
    double getPriceByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r.photo from Room r where r.id = :uuid")
    URL getPhotoByRoomId(@Param(value = "uuid") String uuid);
    @Query("select r from Room r where r.hotel.id = :hotelId")
    List<Room> getRoomsByHotelId(@Param(value = "hotelId") String hotelId);
}
