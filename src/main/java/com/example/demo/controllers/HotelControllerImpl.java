package com.example.demo.controllers;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Room;
import com.example.demo.service.HotelService;
import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.HotelController;
import com.example.quickstay_contracts.input.BookingPriceForm;
import com.example.quickstay_contracts.viewmodel.*;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelControllerImpl implements HotelController {
    private HotelService hotelService;
    private UserService userService;
    private RoomService roomService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

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
    public String getHotelRoomsByDate(@ModelAttribute("hotelForm") RoomBookingForm form, Model model, RedirectAttributes redirectAttributes, HttpSession session, Principal principal) {
        if (form.start().isAfter(form.end())) {
            return "date-error";
        }

        if (form.start().isBefore(LocalDate.now())) {
            return "date-error";
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
        model.addAttribute("hotelBookingForm", new BookingPriceForm());
        model.addAttribute("title", "Hotel");

        String hotelName = hotelService.findById(form.hotelUUID()).getName();
        String hotelDescription = hotelService.findById(form.hotelUUID()).getDescription();

        session.setAttribute("hotelName", hotelName);
        session.setAttribute("hotelDescription", hotelDescription);
        session.setAttribute("startLocalDate", form.start());
        session.setAttribute("endLocalDate", form.end());
        session.setAttribute("userUUID", form.userUUID());

        redirectAttributes.addFlashAttribute("title", "Hotel");
        redirectAttributes.addFlashAttribute("hotelName", hotelName);
        redirectAttributes.addFlashAttribute("startLocalDate", session.getAttribute("startLocalDate"));
        redirectAttributes.addFlashAttribute("endLocalDate", session.getAttribute("endLocalDate"));

        LOG.log(Level.INFO, "Get all rooms for Hotel: " + hotelName + " for user: " + principal.getName());

        return "hotel";
    }

    @PostMapping("/createBooking/{roomUUID}")
    public String createBooking(@PathVariable String roomUUID, @ModelAttribute("hotelBookingForm") BookingPriceForm form, BindingResult bindingResult, Model model, HttpSession session, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hotelBookingForm", form);
            return "";
        }

        String hotelUUID = roomService.findById(roomUUID).getHotel().getId();

        String userUUID = (String) session.getAttribute("userUUID");
        double balance = userService.getBalanceById(userUUID);
        double totalPrice = roomService.findById(roomUUID).getPrice();

        LocalDate startLocalDate = (LocalDate) session.getAttribute("startLocalDate");
        LocalDate endLocalDate = (LocalDate) session.getAttribute("endLocalDate");

        form = new BookingPriceForm(roomUUID, userUUID, startLocalDate, endLocalDate);

        if (balance < totalPrice) {
            session.setAttribute("badMessage", "У вас недостаточно средств!");
            LOG.log(Level.INFO, "Could not create booking from: " + startLocalDate + " to: " + endLocalDate + " roomUUID: " + roomUUID + " for user: " + principal.getName() + " due to lack of money");

            return "redirect:/hotel/getRoomsByDate?hotelUUID=" + hotelUUID + "&start=" + form.getDateStart() + "&end=" + form.getDateEnd() + "&page=1&size=10&userUUID=" + form.getUserUUID();
        }

        userService.createBooking(form);

        session.setAttribute("successMessage", "Вы успешно забронировали номер!");

        LOG.log(Level.INFO, "Created booking from: " + startLocalDate + " to: " + endLocalDate + " roomUUID: " + roomUUID + " for user: " + principal.getName());

        return "redirect:/hotel/getRoomsByDate?hotelUUID=" + hotelUUID + "&start=" + form.getDateStart() + "&end=" + form.getDateEnd() + "&page=1&size=10&userUUID=" + form.getUserUUID();
    }

    @GetMapping("/{id}")
    Hotel getHotel(@PathVariable String id, Principal principal) {
        LOG.log(Level.INFO, "Get hotel by hotelUUID: " + id + " for user: " + principal.getName());

        return hotelService.findById(id);
    }

    @GetMapping("/{id}/rating")
    Double getHotelRating(@PathVariable String id, Principal principal) {
        LOG.log(Level.INFO, "Get hotel rating by hotelUUID: " + id + " for user: " + principal.getName());

        return hotelService.findById(id).getRating();
    }

    @GetMapping("/getAll")
    public List<Hotel> getAllHotels(Principal principal) {
        LOG.log(Level.INFO, "Get all hotels for user: " + principal.getName());

        return hotelService.findAll();
    }

    @GetMapping("/getRoomById/{roomUUID}")
    public Room getRoomById(@PathVariable String roomUUID, Principal principal) {
        LOG.log(Level.INFO, "Get Room by roomUUID: " + roomUUID + " for user: " + principal.getName());

        return roomService.findById(roomUUID);
    }

    @GetMapping("/getAllRooms")
    public List<Room> getAllRooms(Principal principal) {
        LOG.log(Level.INFO, "Get all rooms for user: " + principal.getName());

        return roomService.findAll();
    }

    @GetMapping("/getHotelRooms/{hotelUUID}")
    public List<Room> getHotelRooms(@PathVariable String hotelUUID, Principal principal) {
        LOG.log(Level.INFO, "Get all hotel rooms by hotelUUID: " + hotelUUID + " for user: " + principal.getName());

        return hotelService.getAllRooms(hotelUUID);
    }
}
