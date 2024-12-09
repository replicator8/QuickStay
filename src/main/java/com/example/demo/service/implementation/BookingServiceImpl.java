package com.example.demo.service.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.viewmodel.AdminBookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingForm;
import com.example.quickstay_contracts.viewmodel.BookingFilterForm;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    public BookingServiceImpl(UserRepository userRepository, RoomRepository roomRepository, HotelRepository hotelRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking findById(String uuid) {
        return bookingRepository.findById(uuid).get();
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }

    @Override
    public boolean deleteBooking(String bookingUUID) {
        bookingRepository.deleteById(bookingUUID);
        return true;
    }

    @Override
    public Page<HotelViewModel> getHotels(BookingForm model) {
        Pageable pageable = PageRequest.of(model.page() - 1, model.size(), Sort.by(Sort.Direction.DESC,"rating"));
        String country = model.country();
        String city = model.city();
        double rating = model.rating();
        // TODO: session storage save data's

        if (city.isEmpty()) {
            Page<Hotel> hotels = hotelRepository.getAllHotels(pageable);
            return hotels.map(hotel -> new HotelViewModel(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getRating(), hotel.getPhoto()));
        }

        if (rating != 0.0) {
            Page<Hotel> hotels = hotelRepository.getHotelByCountryAndCityFilter(country, city, rating, pageable);
            return hotels.map(hotel -> new HotelViewModel(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getRating(), hotel.getPhoto()));
        }
        Page<Hotel> hotels = hotelRepository.getHotelByCountryAndCity(country, city, pageable);

        return hotels.map(hotel -> new HotelViewModel(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getRating(), hotel.getPhoto()));
    }

    @Override
    public Page<HotelViewModel> getHotelsWithFilter(BookingForm model) {
        Page<HotelViewModel> hotels = getHotels(model);
        return hotels;
    }

    @Override
    public List<AdminBookingViewModel> getAdminBookingsForHotelName(String hotelName) {
        List<Booking> bookings = bookingRepository.getHotelBookingsByName(hotelName);
        List<AdminBookingViewModel> hotelBookings = new ArrayList<>();

        int cnt = 1;
        for (Booking value: bookings) {
            User user = value.getUser();
            String description = value.getDateStart().getDayOfMonth() + " " + value.getDateStart().getMonth() + " - " + value.getDateEnd().getDayOfMonth() + " " + value.getDateEnd().getMonth() + " for: " + value.getPrice() + " RUB." + "\n" + value.getRoom().getHotel().getName() + ", " + value.getRoom().getHotel().getAddress().getFullAddress() + "\n" + value.getRoom().getRoomType().getRus() + " номер";

            hotelBookings.add(new AdminBookingViewModel(
                    "Бронирование " + cnt,
                  description,
                    user.getUserName()
            ));
            cnt++;
        }

        return hotelBookings;
    }
}
