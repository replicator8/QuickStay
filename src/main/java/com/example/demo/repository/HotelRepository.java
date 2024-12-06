package com.example.demo.repository;

import com.example.demo.constants.RoomType;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.net.URL;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    Hotel findByName(String name);
    @Query("select h.rating from Hotel h where h.id = :uuid")
    double getRatingByHotelId(@Param(value = "uuid") String uuid);
    @Query("select h.roomsCount from Hotel h where h.id = :uuid")
    int getRoomsCountByHotelId(@Param(value = "uuid") String uuid);
    @Query("select h.photo from Hotel h where h.id = :uuid")
    URL getPhotoByHotelId(@Param(value = "uuid") String uuid);
    @Query(value = "select r from Room r where r.hotel.id = :uuid")
    List<Room> getAllRooms(@Param(value = "uuid") String uuid);
    @Query(value = "select r from Room r where r.hotel.id = :uuid and r.roomType = :type")
    List<Room> getAllRoomsByType(@Param(value = "uuid") String uuid, @Param(value = "type") RoomType type);
    @Transactional
    @Modifying
    @Query("UPDATE Hotel h SET h.rating = :newRating WHERE h.id = :hotelId")
    void addUserRating(@Param(value = "newRating") double newRating, @Param(value = "hotelId") String hotelId);
    @Query("select h from Hotel h where h.address.country = :country and h.address.city = :city")
    List<Hotel> getHotelByCountryAndCity(@Param(value = "country") String country, @Param(value = "city") String city);
    @Query("select h from Hotel h where h.address.country = :country and h.address.city = :city and h.rating >= :rating and h.rating < :rating + 1")
    List<Hotel> getHotelByCountryAndCityFilter(@Param(value = "country") String country, @Param(value = "city") String city, @Param(value = "rating") double rating);
}
