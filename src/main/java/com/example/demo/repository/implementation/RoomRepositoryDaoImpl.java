package com.example.demo.repository.implementation;

import com.example.demo.domain.Room;
import com.example.demo.repository.GenericRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryDaoImpl extends GenericRepository<Room, String> implements RoomRepository {
    public RoomRepositoryDaoImpl() {
        super(Room.class);
    }
}
