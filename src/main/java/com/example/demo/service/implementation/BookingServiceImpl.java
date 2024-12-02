package com.example.demo.service.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Hotel;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import com.example.quickstay_contracts.viewmodel.BookingViewModel;
import com.example.quickstay_contracts.viewmodel.BookingViewModelFilter;
import com.example.quickstay_contracts.viewmodel.HotelViewModel;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<HotelViewModel> getHotels(BookingViewModel model) {
        String country = model.country();
        String city = model.city();

        List<Hotel> hotels = hotelRepository.getHotelByCountryAndCity(country, city);
        List<HotelViewModel> models = new ArrayList<>(hotels.size());

        for (Hotel hotel: hotels) {
            models.add(new HotelViewModel(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getRating(), hotel.getPhoto()));
        }

        return models;
    }

    @Override
    public List<HotelViewModel> getHotelsWithFilter(BookingViewModelFilter model) {
        String country = model.country();
        String city = model.city();
        double rating = model.rating();

        List<Hotel> hotels = hotelRepository.getHotelByCountryAndCityFilter(country, city, rating);
        List<HotelViewModel> models = new ArrayList<>(hotels.size());

        for (Hotel hotel: hotels) {
            models.add(new HotelViewModel(hotel.getId(), hotel.getName(), hotel.getDescription(), hotel.getRating(), hotel.getPhoto()));
        }

        return models;
    }
}
