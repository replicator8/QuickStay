package com.example.demo.service;

import com.example.demo.domain.Room;
import com.example.quickstay_contracts.input.CustomBookingForm;
import com.example.quickstay_contracts.viewmodel.RoomViewModelCustom;
import org.springframework.data.domain.Page;
import java.util.List;

public interface RoomService {
    Room findById(String uuid);
    void setDiscount(String uuid, int percentageNumber);
    Page<RoomViewModelCustom> getCustomRooms(CustomBookingForm model);
    List<Room> findAll();
}
