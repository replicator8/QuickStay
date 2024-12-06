package com.example.demo.controllers;

import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.RegisterController;
import com.example.quickstay_contracts.viewmodel.UserRegisterViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterControllerImpl implements RegisterController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // MARK: ok
    @Override
    @PostMapping("/signUp")
    public void signUp(UserRegisterViewModel userRegisterViewModel) {
        userService.createUser(userRegisterViewModel);
        // TODO: open user acc page
    }
}