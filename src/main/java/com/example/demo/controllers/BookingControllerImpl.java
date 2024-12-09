package com.example.demo.controllers;

import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.controllers.BookingController;
import com.example.quickstay_contracts.viewmodel.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/bookings")
public class BookingControllerImpl implements BookingController {
    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @GetMapping("/getHotels")
    public String getHotels(@ModelAttribute("bookingForm") BookingForm form, Model model) {
        var country = form.country() != null ? form.country() : "Россия";
        var city = form.city() != null ? form.city() : "";
        var start = form.start() != null ? form.start() : LocalDate.now();
        var end = form.end() != null ? form.end() : start.plusDays(1);
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 10;
        var rating = form.rating() != null ? form.rating() : 0.0;

        form = new BookingForm(country, city, start, end, page, size, rating);

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

        return "booking";
    }

    @Override
    @GetMapping("/getHotelsWithFilter")
    public String getHotelsWithFilter(@ModelAttribute("bookingForm") BookingForm form, Model model) {
        var country = form.country() != null ? form.country() : "Россия";
        var city = form.city() != null ? form.city() : "";
        var start = form.start() != null ? form.start() : LocalDate.now();
        var end = form.end() != null ? form.end() : start.plusDays(1);
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 10;
        var rating = form.rating() != null ? 5.0 : 0.0;

        form = new BookingForm(country, city, start, end, page, size, rating);

        Page<HotelViewModel> hotels = bookingService.getHotelsWithFilter(form);
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

        return "booking";
    }

    @GetMapping("/hotelDetails")
    public String hotelDetails(@RequestParam("hotelName") String hotelName, @RequestParam("hotelDescription") String hotelDescription, Model model, HttpSession session) {
        System.out.println("Selected hotel: " + hotelName);
        System.out.println("Hotel description: " + hotelDescription);

        model.addAttribute("title", "Hotel");
        model.addAttribute("hotelName", hotelName);
        model.addAttribute("hotelDescription", hotelDescription);

        System.out.println("User UUID: " + session.getAttribute("userUUID"));

        return "hotel";
    }

}
