package com.example.demo.service.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.input.AdminBookingForm;
import com.example.quickstay_contracts.viewmodel.AdminBookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingForm;
import com.example.quickstay_contracts.viewmodel.BookingFilterForm;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public Page<AdminBookingViewModel> getAdminBookingsForHotelName(AdminBookingForm model) {
        Pageable pageable = PageRequest.of(model.page() - 1, model.size());

        if (model.hotelName().isEmpty()) {
            Page<Booking> bookings = bookingRepository.getAllBookings(pageable);
            List<AdminBookingViewModel> hotelBookings = new ArrayList<>();

            int cnt = model.page() == 1 ? 1 : ((model.page() - 1) * 10 + 1);
            for (Booking value: bookings) {
                User user = value.getUser();
                String date = value.getDateStart().getDayOfMonth() + " " + value.getDateStart().getMonth() + " - " + value.getDateEnd().getDayOfMonth() + " " + value.getDateEnd().getMonth();
                String address = value.getRoom().getHotel().getAddress().getFullAddress();
                String price = value.getPrice() + " руб.";
                String hotelName = value.getRoom().getHotel().getName();
                String roomType = value.getRoom().getRoomType().getRus() + " номер";
                String roomUUID = value.getRoom().getId();

                hotelBookings.add(new AdminBookingViewModel(
                        value.getId(),
                        "Бронирование " + cnt,
                        date,
                        price,
                        hotelName,
                        address,
                        roomType,
                        roomUUID,
                        user.getUserName()
                ));
                cnt++;
            }
            return new PageImpl<>(hotelBookings, pageable, bookings.getTotalElements());
        }

        Page<Booking> bookings = bookingRepository.getHotelBookingsByName(model.hotelName(), pageable);
        List<AdminBookingViewModel> hotelBookings = new ArrayList<>();

        int cnt = model.page() == 1 ? 1 : ((model.page() - 1) * 10 + 1);
        for (Booking value: bookings) {
            User user = value.getUser();
            String date = value.getDateStart().getDayOfMonth() + " " + value.getDateStart().getMonth() + " - " + value.getDateEnd().getDayOfMonth() + " " + value.getDateEnd().getMonth();
            String address = value.getRoom().getHotel().getAddress().getFullAddress();
            String price = value.getPrice() + " руб.";
            String hotelName = value.getRoom().getHotel().getName();
            String roomType = value.getRoom().getRoomType().getRus() + " номер";
            String roomUUID = value.getRoom().getId();

            hotelBookings.add(new AdminBookingViewModel(
                    value.getId(),
                    "Бронирование " + cnt,
                    date,
                    price,
                    hotelName,
                    address,
                    roomType,
                    roomUUID,
                    user.getUserName()
            ));
            cnt++;
        }

        return new PageImpl<>(hotelBookings, pageable, bookings.getTotalElements());
    }
}
