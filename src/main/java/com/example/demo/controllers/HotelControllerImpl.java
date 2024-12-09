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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
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

    @Override
    @PostMapping("/getRoomsByDate")
    public List<RoomViewModel> getHotelRoomsByDate(RoomBookingModel roomBookingModel) {
        if (roomBookingModel.start().isAfter(roomBookingModel.end())) {
            throw new IllegalArgumentException("Неверный порядок дат");
        }

        if (roomBookingModel.start().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Выберите правильную дату");
        }

        List<RoomViewModel> rooms = hotelService.getAllFreeRoomsByDates(roomBookingModel);

        return rooms;
    }

    // MARK: ok
    @Override
    @PostMapping("/getRoomsByDateWithFilter")
    public List<RoomViewModel> getHotelRoomsByDateWithFilter(@RequestBody RoomBookingModelFilter roomBookingModelFilter) {
        if (roomBookingModelFilter.start().isAfter(roomBookingModelFilter.end())) {
            throw new IllegalArgumentException("Неверный порядок дат");
        }

        if (roomBookingModelFilter.start().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Выберите правильную дату");
        }
        List<RoomViewModel> rooms = hotelService.getAllFreeRoomsByDatesFilter(roomBookingModelFilter);
        // TODO: add to page

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

    // MARK: ok
    @GetMapping("/getAllRooms")
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }

    // MARK: ok
    @GetMapping("/getHotelRooms/{hotelUUID}")
    public List<Room> getHotelRooms(@PathVariable String hotelUUID) {
        return hotelService.getAllRooms(hotelUUID);
    }
}
