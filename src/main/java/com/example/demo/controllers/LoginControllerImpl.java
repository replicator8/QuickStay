package com.example.demo.controllers;

import com.example.quickstay_contracts.controllers.LoginController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {
    @GetMapping("/")
    public String signIn() {
        return "login";
    }

}
