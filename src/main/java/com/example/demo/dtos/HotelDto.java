package com.example.demo.dtos;

import com.example.demo.domain.Address;
import com.example.demo.domain.Room;
import java.util.List;

public class HotelDto {
    private String id;
    private String name;
    private Double rating;
    private Address address;
    private List<Room> roomList;

    public HotelDto(String id, String name, Double rating, Address address, List<Room> roomList) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.roomList = roomList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
