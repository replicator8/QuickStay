package com.example.demo;

import com.example.demo.constants.UserRoles;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repository.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private final UserRoleRepository userRoleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final String defaultPassword;

    Faker fakerRus = new Faker(new Locale("ru"));
    Faker fakerUSA = new Faker(new Locale("en-US"));
    Random rand = new Random();

    public MyCommandLineRunner(HotelRepository hotelRepository, AddressRepository addressRepository, UserRepository userRepository, RoomRepository roomRepository, BookingRepository bookingRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            var adminRole = new Role(UserRoles.ADMIN);
            var normalUserRole = new Role(UserRoles.USER);
            userRoleRepository.save(adminRole);
            userRoleRepository.save(normalUserRole);
        }
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            initAdmin();
            initNormalUser();
        }
    }

    private void initAdmin(){
        var adminRole = userRoleRepository.findRoleByName(UserRoles.ADMIN).orElseThrow();

        double balance = Double.parseDouble(String.format("%.2f", rand.nextDouble(30_000)));
        var adminUser = new User("Admin", "Administrator", "admin8", passwordEncoder.encode(defaultPassword), 20, balance);
        adminUser.setRoles(List.of(adminRole));

        userRepository.save(adminUser);
    }

    private void initNormalUser(){
        var userRole = userRoleRepository.findRoleByName(UserRoles.USER).orElseThrow();

        for (int i = 0; i < 20; i++) {
            double balance = Double.parseDouble(String.format("%.2f", rand.nextDouble(300_000)));
            var normalUser = new User(fakerRus.name().firstName(), fakerRus.name().lastName(), fakerRus.name().username(), passwordEncoder.encode(defaultPassword), rand.nextInt(18, 60), balance);
            normalUser.setRoles(List.of(userRole));

            userRepository.save(normalUser);
        }

    }

}
