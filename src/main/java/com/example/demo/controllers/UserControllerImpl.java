package com.example.demo.controllers;

import com.example.demo.domain.Booking;
import com.example.demo.domain.User;
import com.example.demo.service.BookingService;
import com.example.demo.service.HotelService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.UserController;
import com.example.quickstay_contracts.input.UserBookingsForm;
import com.example.quickstay_contracts.viewmodel.*;
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
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private UserService userService;
    private HotelService hotelService;
    private BookingService bookingService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @GetMapping("/getActiveBookings")
    public String getActiveBookings(@ModelAttribute("userABForm") UserBookingsForm form, Model model, HttpSession session, Principal principal) {
        var userUUID = form.userUUID() != null ? form.userUUID() : (String) session.getAttribute("userUUID");
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 30;
        form = new UserBookingsForm(userUUID, page, size);

        Page<UserActiveBookingsViewModel> bookings = userService.getActiveBookings(form);

        var bookingViewModels = bookings.stream()
                .map(booking -> new UserActiveBookingsViewModel(booking.uuid(), booking.title(), booking.date(), booking.price(), booking.hotelName(), booking.address(), booking.photo()))
                .toList();

        var viewModel = new UserActiveBookingsListViewModel(
                bookingViewModels,
                bookings.getTotalPages()
        );

        User user = getUser(userUUID);
        Double balance = Double.parseDouble(String.format("%.2f", user.getBalance()));
        String userName = user.getUserName();
        int userAge = user.getAge();

        session.setAttribute("userBalance", balance);
        session.setAttribute("userName", userName);
        session.setAttribute("userAge", userAge);
        session.removeAttribute("successMessage");
        session.removeAttribute("badMessage");

        model.addAttribute("userABForm", form);
        model.addAttribute("isArchive", false);
        model.addAttribute("model", viewModel);
        model.addAttribute("title", "User profile");

        LOG.log(Level.INFO, "Get active bookings for user: " + principal.getName());

        return "user";
    }

    @Override
    @GetMapping("/getArchiveBookings")
    public String getArchiveBookings(@ModelAttribute("userABForm") UserBookingsForm form, Model model, HttpSession session, Principal principal) {
        var userUUID = form.userUUID() != null ? form.userUUID() : (String) session.getAttribute("userUUID");
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 10;
        form = new UserBookingsForm(userUUID, page, size);

        Page<UserArchiveBookingsViewModel> bookings = userService.getArchiveBookings(form);

        var bookingViewModels = bookings.stream()
                .map(booking -> new UserActiveBookingsViewModel(booking.uuid(), booking.title(), booking.date(), booking.price(), booking.hotelName(), booking.address(), booking.photo()))
                .toList();

        var viewModel = new UserActiveBookingsListViewModel(
                bookingViewModels,
                bookings.getTotalPages()
        );

        User user = getUser(userUUID);
        Double balance = user.getBalance();
        String userName = user.getUserName();
        int userAge = user.getAge();

        session.setAttribute("userBalance", balance);
        session.setAttribute("userName", userName);
        session.setAttribute("userAge", userAge);

        model.addAttribute("userABForm", form);
        model.addAttribute("model", viewModel);
        model.addAttribute("isArchive", true);
        model.addAttribute("title", "User profile");

        LOG.log(Level.INFO, "Get archive bookings for user: " + principal.getName());

        return "user";
    }

    @Override
    @GetMapping("/cancelBooking/{bookingUUID}")
    public String cancelBooking(@PathVariable String bookingUUID, Principal principal) {
        Booking booking = bookingService.findById(bookingUUID);
        User user = booking.getUser();
        double price = booking.getPrice();
        user.setBalance(user.getBalance() + price);

        bookingService.deleteBooking(bookingUUID);

        LOG.log(Level.INFO, "Cancel booking by bookingUUID: " + bookingUUID + " for user: " + principal.getName());

        return "redirect:/users/getActiveBookings";
    }

    @Override
    @GetMapping("/rateBooking/{bookingUUID}")
    public String rateBooking(@PathVariable String bookingUUID, @RequestParam double rating, Principal principal) {
        String hotelUUID = bookingService.findById(bookingUUID).getRoom().getHotel().getId();
        hotelService.updateRating(hotelUUID, rating);

        LOG.log(Level.INFO, "Update rating to " + rating + " by bookingUUID: " + bookingUUID + " for user: " + principal.getName());

        return "redirect:/users/getArchiveBookings";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session, Principal principal) {
        LOG.log(Level.INFO, "Logout for user: " + principal.getName());

        session.invalidate();

        return "redirect:/login/";
    }

    @GetMapping("/getAll")
    List<User> getUsers() {
        LOG.log(Level.INFO, "Get all users");

        return userService.findAll();
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable String id) {
        LOG.log(Level.INFO, "Get user by userUUID: " + id);

        return userService.findById(id);
    }

    @GetMapping("/getBalance/{userUUID}")
    Double getBalance(@PathVariable String userUUID, Principal principal) {
        LOG.log(Level.INFO, "Get user balance bu userUUID: " + userUUID);

        return userService.getBalanceById(userUUID);
    }
}
