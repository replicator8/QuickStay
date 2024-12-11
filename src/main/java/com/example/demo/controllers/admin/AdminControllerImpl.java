package com.example.demo.controllers.admin;

import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.controllers.AdminController;
import com.example.quickstay_contracts.input.AdminBookingForm;
import com.example.quickstay_contracts.viewmodel.AdminBookingListViewModel;
import com.example.quickstay_contracts.viewmodel.AdminBookingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {
    private BookingService bookingService;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    @GetMapping("/getAdminAvailableBookings")
    public String getAllBookingsForHotel(@ModelAttribute("adminForm") AdminBookingForm form, Model model) {
        var hotelName = form.hotelName() != null ? form.hotelName() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 10;

        form = new AdminBookingForm(hotelName, page, size);

        Page<AdminBookingViewModel> bookings = bookingService.getAdminBookingsForHotelName(form);
        var bookingViewModels = bookings.stream()
                .map(booking -> new AdminBookingViewModel(booking.bookingUUID(), booking.name(), booking.date(), booking.price(), booking.hotelName(), booking.address(), booking.roomType(), booking.roomUUID(), booking.userName()))
                .toList();

        var viewModel = new AdminBookingListViewModel(
                bookingViewModels,
                bookings.getTotalPages()
        );

        model.addAttribute("adminForm", form);
        model.addAttribute("model", viewModel);
        model.addAttribute("title", "Admin");

        return "admin";
    }

    @Override
    @GetMapping("/cancelBooking/{bookingUUID}")
    public String cancelBooking(@PathVariable String bookingUUID) {
        bookingService.deleteBooking(bookingUUID);

        return "redirect:/admin/getAdminAvailableBookings";
    }
}
