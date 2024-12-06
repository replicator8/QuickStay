package com.example.demo.controllers;

import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.PriceController;
import com.example.quickstay_contracts.input.BookingCreateInputModel;
import com.example.quickstay_contracts.input.CustomBookingInputModel;
import com.example.quickstay_contracts.viewmodel.RoomViewModelCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceControllerImpl implements PriceController {
    private UserService userService;
    private RoomService roomService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    @PostMapping("/getRooms")
    public List<RoomViewModelCustom> getRooms(@RequestBody CustomBookingInputModel customBookingInputModel) {
        // TODO: display rooms
        List<RoomViewModelCustom> rooms = roomService.getCustomRooms(customBookingInputModel);

        return rooms;
    }

    @Override
    @PostMapping("/createBooking")
    public void createBooking(@RequestBody BookingCreateInputModel model) {
        userService.createBooking(model);
    }
}
