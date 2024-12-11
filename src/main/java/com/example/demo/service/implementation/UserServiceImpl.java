package com.example.demo.service.implementation;

import com.example.demo.domain.Booking;
import com.example.demo.domain.Room;
import com.example.demo.domain.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.input.BookingPriceForm;
import com.example.quickstay_contracts.viewmodel.UserActiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserArchiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserRegisterForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository, HotelRepository hotelRepository, BookingRepository bookingRepository) {
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
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findById(String uuid) {
        return userRepository.findById(uuid).get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public double getBalanceById(String uuid) {
        return findById(uuid).getBalance();
    }

    @Override
    public BookingPriceForm createBooking(BookingPriceForm bookingDto) {

        int totalDays = (int) ChronoUnit.DAYS.between(bookingDto.getDateStart(), bookingDto.getDateEnd());
        for (int i = 0; i < totalDays; i++) {
            if (!bookingRepository.checkAvailability(bookingDto.getRoomUUID(), bookingDto.getDateStart().plusDays(i))) return null;
        }

        Room room = roomRepository.findById(bookingDto.getRoomUUID()).get();
        User user = userRepository.findById(bookingDto.getUserUUID()).get();
        double totalPrice = Double.parseDouble(String.format("%.2f", roomRepository.getPriceByRoomId(bookingDto.getRoomUUID()) * totalDays));
        Booking booking = new Booking(room, user, bookingDto.getDateStart(), bookingDto.getDateEnd(), totalPrice, totalDays);

        bookingRepository.save(booking);
        return bookingDto;
    }

    @Override
    public boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = getUserBookings(userId);
        for (Booking booking: bookings) {
            if (Objects.equals(booking.getRoom().getId(), roomId) && booking.getDateStart() == startDate && booking.getDateEnd() == endDate) {
                bookingRepository.delete(booking);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.getUserBookings(userId);
    }

    @Override
    public List<UserActiveBookingsViewModel> getActiveBookings(String userUUID) {
        List<Booking> bookings = getUserBookings(userUUID);

        for(Booking booking: bookings) {
            if (!booking.getDateStart().isAfter(LocalDate.now())) {
                bookings.remove(booking);
            }
        }

        List<UserActiveBookingsViewModel> userBookings = new ArrayList<>(bookings.size());

        int cnt = 1;
        for (Booking booking: bookings) {
            String title = "Бронирование " + cnt;
            String description = booking.getDateStart().getDayOfMonth() + " " + booking.getDateStart().getMonth() + " - " + booking.getDateEnd().getDayOfMonth() + " " + booking.getDateEnd().getMonth() + " for: " + booking.getPrice() + " RUB.";
            URL photo = booking.getRoom().getPhoto();

            userBookings.add(new UserActiveBookingsViewModel(
                    booking.getId(), title, description, photo
            ));
            cnt++;
        }

        return userBookings;
    }

    @Override
    public List<UserArchiveBookingsViewModel> getArchiveBookings(String userUUID) {
        List<Booking> bookings = getUserBookings(userUUID);
        for(Booking booking: bookings) {
            if (!booking.getDateStart().isBefore(LocalDate.now())) {
                bookings.remove(booking);
            }
        }

        List<UserArchiveBookingsViewModel> userBookings = new ArrayList<>(bookings.size());

        int cnt = 1;
        for (Booking booking: bookings) {
            String title = "Бронирование " + cnt;
            String description = booking.getDateStart().getDayOfMonth() + " " + booking.getDateStart().getMonth() + " - " + booking.getDateEnd().getDayOfMonth() + " " + booking.getDateEnd().getMonth() + " for: " + booking.getPrice();
            URL photo = booking.getRoom().getPhoto();

            userBookings.add(new UserArchiveBookingsViewModel(
                    booking.getId(), title, description, photo
            ));
            cnt++;
        }

        return userBookings;
    }

    @Override
    public void createUser(UserRegisterForm userModel) {
        String firstName = userModel.firstName();
        String lastName = userModel.lastName();
        String userName = userModel.userName();
        String password = userModel.password();
        int age = userModel.age();
        String strBalance = String.format("%.2f", new Random().nextDouble(100_000));
        double balance = Double.parseDouble(strBalance);

        User user = new User(firstName, lastName, userName, password, age, balance);
        userRepository.save(user);
    }
}
