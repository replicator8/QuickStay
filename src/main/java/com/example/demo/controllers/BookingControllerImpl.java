package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.service.BookingService;
import com.example.demo.service.HotelService;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.BookingController;
import com.example.quickstay_contracts.viewmodel.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/bookings")
@EnableCaching
public class BookingControllerImpl implements BookingController {
    private BookingService bookingService;
    private HotelService hotelService;
    private UserService userService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/getHotels")
    @Cacheable(value = "hotels")
    public String getHotels(@ModelAttribute("bookingForm") BookingForm form, Model model, Principal principal, HttpSession session) {
        var country = form.country() != null ? form.country() : "Россия";
        var city = form.city() != null ? form.city() : "";
        var start = form.start() != null ? form.start() : LocalDate.now();
        var end = form.end() != null ? form.end() : start.plusDays(1);
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 10;
        var rating = form.rating() != null ? form.rating() : 0.0;
        var filter = form.filter() != null ? form.filter() : "false";

        form = new BookingForm(country, city, start, end, page, size, rating, filter);

        Page<HotelViewModel> hotels = bookingService.getHotels(form);
        var hotelViewModels = hotels.stream()
                .map(hotel -> new HotelViewModel(hotel.id(), hotel.name(), hotel.description(), hotel.rating(), hotel.photo()))
                .toList();

        var viewModel = new HotelListViewModel(
                hotelViewModels,
                hotels.getTotalPages()
        );

        model.addAttribute("bookingForm", form);
        model.addAttribute("model", viewModel);
        model.addAttribute("title", "Booking");

        User user = userService.findByUserName(principal.getName());
        session.setAttribute("userUUID", user.getId());
        session.removeAttribute("successMessage");
        session.removeAttribute("badMessage");

        return "booking";
    }

    @GetMapping("/hotelDetails")
    public String hotelDetails(@RequestParam("hotelName") String hotelName, @RequestParam("hotelDescription") String hotelDescription, @RequestParam("start") String start, @RequestParam("end") String end, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        System.out.println("Selected hotel: " + hotelName);
        System.out.println("Hotel description: " + hotelDescription);
        System.out.println("Start date: " + start);
        System.out.println("End date: " + end);

        String hotelUUID = hotelService.findByName(hotelName).getId();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        String startRes = startDate.getDayOfMonth() + " " + startDate.getMonth() + " " + startDate.getYear();
        String endRes = endDate.getDayOfMonth() + " " + endDate.getMonth() + " " + endDate.getYear();

        redirectAttributes.addFlashAttribute("title", "Hotel");
        redirectAttributes.addFlashAttribute("hotelName", hotelName);
        redirectAttributes.addFlashAttribute("hotelDescription", hotelDescription);

        session.setAttribute("startDate", startRes);
        session.setAttribute("endDate", endRes);
        session.removeAttribute("successMessage");
        session.removeAttribute("badMessage");

        redirectAttributes.addFlashAttribute("startLocalDate", startDate);
        redirectAttributes.addFlashAttribute("endLocalDate", endDate);

        String userUUID = session.getAttribute("userUUID").toString();

        return "redirect:/hotel/getRoomsByDate?hotelUUID=" + hotelUUID + "&start=" + start + "&end=" + end + "&page=1&size=10&userUUID=" + userUUID;
    }

}
