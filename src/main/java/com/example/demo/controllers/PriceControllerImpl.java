package com.example.demo.controllers;

import com.example.demo.service.RoomService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.PriceController;
import com.example.quickstay_contracts.input.BookingPriceForm;
import com.example.quickstay_contracts.input.CustomBookingForm;
import com.example.quickstay_contracts.viewmodel.RoomCustomListViewModel;
import com.example.quickstay_contracts.viewmodel.RoomViewModelCustom;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/price")
public class PriceControllerImpl implements PriceController {
    private UserService userService;
    private RoomService roomService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    @GetMapping("/getRooms")
    public String getRooms(@ModelAttribute("priceForm") CustomBookingForm form, Model model, HttpSession session, Principal principal) {
        var price = form.price() != null ? form.price() : 1000.0;
        var start = form.start() != null ? form.start() : LocalDate.now();
        var end = form.end() != null ? form.end() : start.plusDays(1);
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 10;
        form = new CustomBookingForm(price, start, end, page, size);

        Page<RoomViewModelCustom> rooms = roomService.getCustomRooms(form);

        var roomViewModels = rooms.stream()
                .map(room -> new RoomViewModelCustom(room.roomUUID(), room.hotelName(), room.type(), room.address(), room.price(), room.photo()))
                .toList();

        var viewModel = new RoomCustomListViewModel(
                roomViewModels,
                rooms.getTotalPages()
        );

        session.setAttribute("startLocalDate", form.start());
        session.setAttribute("endLocalDate", form.end());

        model.addAttribute("priceForm", form);
        model.addAttribute("model", viewModel);
        model.addAttribute("priceBookingForm", new BookingPriceForm());
        model.addAttribute("title", "Price");

        LOG.log(Level.INFO, "Get rooms on Price controller from " + form.start() + " to: " + form.end() + " for amount: " + form.price() + " for user: " + principal.getName());

        return "price";
    }

    @Override
    @PostMapping("/createBooking/{roomUUID}")
    public String createBooking(@PathVariable String roomUUID, @ModelAttribute("priceBookingForm") BookingPriceForm form, HttpSession session, Principal principal) {
        String userUUID = (String) session.getAttribute("userUUID");
        LocalDate startLocalDate = (LocalDate) session.getAttribute("startLocalDate");
        LocalDate endLocalDate = (LocalDate) session.getAttribute("endLocalDate");
        double balance = userService.getBalanceById(userUUID);
        double totalPrice = roomService.findById(roomUUID).getPrice();

        form = new BookingPriceForm(roomUUID, userUUID, startLocalDate, endLocalDate);

        if (balance < totalPrice) {
            session.setAttribute("badMessage", "У вас недостаточно средств!");
            LOG.log(Level.INFO, "Could not create booking from: " + startLocalDate + " to: " + endLocalDate + " roomUUID: " + roomUUID + " for user: " + principal.getName() + " due to lack of money");

            return "redirect:/price/getRooms";
        }

        session.setAttribute("successMessage", "Вы успешно забронировали номер!");

        userService.createBooking(form);

        LOG.log(Level.INFO, "Created booking from: " + startLocalDate + " to: " + endLocalDate + " roomUUID: " + roomUUID + " for user: " + principal.getName());

        return "redirect:/price/getRooms";
    }
}
