package com.example.demo.controllers;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.service.HotelService;
import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.HotelController;
import com.example.quickstay_contracts.input.BookingCreateInputModel;
import com.example.quickstay_contracts.viewmodel.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/getRoomsByDate")
    public String getHotelRoomsByDate(@ModelAttribute("hotelForm") RoomBookingForm form, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        if (form.start().isAfter(form.end())) {
            throw new IllegalArgumentException("Неверный порядок дат");
        }

        if (form.start().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Выберите правильную дату");
        }

        Page<RoomViewModel> rooms = hotelService.getAllFreeRoomsByDates(form);
        var roomViewModels = rooms.stream()
                .map(room -> new RoomViewModel(room.roomUUID(), room.type(), room.description(), room.price(), room.photo()))
                .toList();

        var viewModel = new RoomListViewModel(
                roomViewModels,
                rooms.getTotalPages()
        );

        model.addAttribute("hotelForm", form);
        model.addAttribute("model", viewModel);
        model.addAttribute("title", "Hotel");

        String hotelName = hotelService.findById(form.hotelUUID()).getName();
        String hotelDescription = hotelService.findById(form.hotelUUID()).getDescription();
        System.out.println("USER UUID: " + session.getAttribute("userUUID"));

        redirectAttributes.addFlashAttribute("title", "Hotel");
        redirectAttributes.addFlashAttribute("hotelName", hotelName);
        redirectAttributes.addFlashAttribute("hotelDescription", hotelDescription);
        redirectAttributes.addFlashAttribute("startDate", form.start());
        redirectAttributes.addFlashAttribute("endDate", form.end());
        redirectAttributes.addFlashAttribute("userUUID", session.getAttribute("userUUID"));

        session.setAttribute("startLocalDate", form.start());
        session.setAttribute("endLocalDate", form.end());

        return "hotel";
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
    public void createBooking(@ModelAttribute("hotelForm") BookingCreateInputModel model) {
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
