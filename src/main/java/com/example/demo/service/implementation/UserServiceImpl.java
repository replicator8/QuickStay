package com.example.demo.service.implementation;

import com.example.demo.constants.UserRoles;
import com.example.demo.domain.Booking;
import com.example.demo.domain.Room;
import com.example.demo.domain.User;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.input.BookingPriceForm;
import com.example.quickstay_contracts.input.UserBookingsForm;
import com.example.quickstay_contracts.viewmodel.UserActiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserArchiveBookingsViewModel;
import com.example.quickstay_contracts.viewmodel.UserRegisterForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).get();
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
        user.setBalance(user.getBalance() - totalPrice);
        Booking booking = new Booking(room, user, bookingDto.getDateStart(), bookingDto.getDateEnd(), totalPrice, totalDays);

        bookingRepository.save(booking);
        userRepository.save(user);
        return bookingDto;
    }

    @Override
    public boolean deleteBooking(String userId, String roomId, LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.getUserBooking(userId);
        for (Booking booking: bookings) {
            if (Objects.equals(booking.getRoom().getId(), roomId) && booking.getDateStart() == startDate && booking.getDateEnd() == endDate) {
                bookingRepository.delete(booking);
                return true;
            }
        }
        return false;
    }



    @Override
    public Page<Booking> getUserBookings(String userId, Pageable pageable) {
        return bookingRepository.getUserBookings(userId, pageable);
    }

    @Override
    public Page<UserActiveBookingsViewModel> getActiveBookings(UserBookingsForm model) {
        Pageable pageable = PageRequest.of(model.page() - 1, model.size());

        Page<Booking> bookings = getUserBookings(model.userUUID(), pageable);

        List<Booking> filteredBookings = bookings.getContent().stream()
                .filter(booking -> !booking.getDateStart().isBefore(LocalDate.now()))
                .toList();

        List<UserActiveBookingsViewModel> userBookings = new ArrayList<>();

        int cnt = 1;
        for (Booking booking : filteredBookings) {
            String title = "Бронирование " + cnt;
            String price = booking.getPrice() + " руб.";
            String date = booking.getDateStart().getDayOfMonth() + " " +
                    booking.getDateStart().getMonth() + " - " +
                    booking.getDateEnd().getDayOfMonth() + " " +
                    booking.getDateEnd().getMonth();
            URL photo = booking.getRoom().getPhoto();
            String hotelName = booking.getRoom().getHotel().getName();
            String address = booking.getRoom().getHotel().getAddress().getFullAddress();

            userBookings.add(new UserActiveBookingsViewModel(
                    booking.getId(), title, date, price, hotelName, address, photo
            ));
            cnt++;
        }

        return new PageImpl<>(userBookings, pageable, filteredBookings.size());
    }

    @Override
    public Page<UserArchiveBookingsViewModel> getArchiveBookings(UserBookingsForm model) {
        Pageable pageable = PageRequest.of(model.page() - 1, model.size());

        Page<Booking> bookings = getUserBookings(model.userUUID(), pageable);

        Iterator<Booking> iterator = bookings.iterator();

        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (!booking.getDateStart().isBefore(LocalDate.now())) {
                iterator.remove();
            }
        }

        List<UserArchiveBookingsViewModel> userBookings = new ArrayList<>();

        int cnt = 1;
        for (Booking booking: bookings) {
            String title = "Бронирование " + cnt;
            String price = booking.getPrice() + " руб.";
            String date = booking.getDateStart().getDayOfMonth() + " " +
                    booking.getDateStart().getMonth() + " - " +
                    booking.getDateEnd().getDayOfMonth() + " " +
                    booking.getDateEnd().getMonth();
            URL photo = booking.getRoom().getPhoto();
            String hotelName = booking.getRoom().getHotel().getName();
            String address = booking.getRoom().getHotel().getAddress().getFullAddress();

            userBookings.add(new UserArchiveBookingsViewModel(
                    booking.getId(), title, date, price, hotelName, address, photo
            ));
            cnt++;
        }

        return new PageImpl<>(userBookings, pageable, userBookings.size());
    }

    @Override
    public void createUser(UserRegisterForm userModel) {
        String firstName = userModel.firstName();
        String lastName = userModel.lastName();
        String userName = userModel.userName();
        String password = passwordEncoder.encode(userModel.password());
        String strBalance = String.format("%.2f", new Random().nextDouble(100_000));
        int age = userModel.age();
        double balance = Double.parseDouble(strBalance);

        var userRole = userRoleRepository.findRoleByName(UserRoles.USER).orElseThrow();

        User user = new User(firstName, lastName, userName, password, age, balance);
        user.setRoles(List.of(userRole));

        userRepository.save(user);
    }
}
