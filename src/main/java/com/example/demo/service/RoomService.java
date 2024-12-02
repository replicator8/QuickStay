package com.example.demo.service;

import com.example.demo.domain.Room;
import com.example.quickstay_contracts.input.CustomBookingInputModel;
import com.example.quickstay_contracts.viewmodel.RoomViewModelCustom;
import java.util.List;

public interface RoomService {
    Room findById(String uuid);
    void setDiscount(String uuid, int percentageNumber);
    List<RoomViewModelCustom> getCustomRooms(CustomBookingInputModel model);
}
