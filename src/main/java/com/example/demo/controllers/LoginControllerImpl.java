package com.example.demo.controllers;

import com.example.quickstay_contracts.controllers.LoginController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @GetMapping("/")
    public String signIn() {
        LOG.log(Level.INFO, "Open login page");

        return "login";
    }

}
