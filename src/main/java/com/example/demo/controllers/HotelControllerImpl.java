package com.example.demo.controllers;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.service.HotelService;
import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.HotelController;
import com.example.quickstay_contracts.input.BookingCreateInputModel;
import com.example.quickstay_contracts.viewmodel.RoomBookingModel;
import com.example.quickstay_contracts.viewmodel.RoomBookingModelFilter;
import com.example.quickstay_contracts.viewmodel.RoomViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelControllerImpl implements HotelController {
    private HotelService hotelService;
    private UserService userService;
    private RoomService roomService;

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    // MARK: invalid check available & crash
    @Override
    @PostMapping("/getRooms")
    public List<RoomViewModel> getHotelRooms(RoomBookingModel roomBookingModel) {
        // TODO: add to page
        List<RoomViewModel> rooms = hotelService.getAllFreeRoomsByDates(roomBookingModel);

        return rooms;
    }

    // MARK: same
    @Override
    @PostMapping("/getRoomsWithFilter")
    public List<RoomViewModel> getHotelRoomsWithFilter(@RequestBody RoomBookingModelFilter roomBookingModelFilter) {
        // TODO: add to page
        List<RoomViewModel> rooms = hotelService.getAllFreeRoomsByDatesFilter(roomBookingModelFilter);

        return rooms;
    }

    // MARK: ok
    @Override
    @PostMapping("/createBooking")
    public void createBooking(@RequestBody BookingCreateInputModel model) {
        userService.createBooking(model);
    }

    // MARK: ok
    @GetMapping("/{id}")
    Hotel getHotel(@PathVariable String id) {
        return hotelService.findById(id);
    }

    // MARK: ok
    @GetMapping("/{id}/rating")
    Double getHotelRating(@PathVariable String id) {
        return hotelService.findById(id).getRating();
    }

    // MARK: ok
    @GetMapping("/getAll")
    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }

    // MARK: ok
    @GetMapping("/getRoomById/{roomUUID}")
    public Room getRoomById(@PathVariable String roomUUID) {
        return roomService.findById(roomUUID);
    }
}
