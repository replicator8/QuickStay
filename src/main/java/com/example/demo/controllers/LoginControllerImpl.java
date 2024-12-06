package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.LoginController;
import com.example.quickstay_contracts.viewmodel.UserAuthViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // MARK: ok
    @Override
    @PostMapping("/auth")
    public String signIn(@RequestBody UserAuthViewModel userAuthViewModel) {
        User user = userService.findByUserName(userAuthViewModel.userName());
        if (user != null) {
            if (user.getPassword().equals(userAuthViewModel.password())) {
                // TODO: ~
                return "Success!";
            }
        }
        return "Try Again!";
    }

}
