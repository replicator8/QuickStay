package com.example.demo.repositories.implementation;

import com.example.demo.domain.Room;
import com.example.demo.repositories.GenericRepository;
import com.example.demo.repositories.RoomRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryDaoImpl extends GenericRepository<Room, String> implements RoomRepository {
    public RoomRepositoryDaoImpl() {
        super(Room.class);
    }
}
