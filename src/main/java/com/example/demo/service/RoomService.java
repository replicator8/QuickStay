package com.example.demo.service;

import com.example.demo.domain.Room;

public interface RoomService {
    Room findById(String uuid);
    void setDiscount(String uuid, int percentageNumber);
}
