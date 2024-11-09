package com.example.demo.service;

import com.example.demo.domain.Room;

public interface RoomService {
    Room findById(String uuid);
}
