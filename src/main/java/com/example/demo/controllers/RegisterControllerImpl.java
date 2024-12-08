package com.example.demo.controllers;

import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.RegisterController;
import com.example.quickstay_contracts.viewmodel.UserRegisterForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterControllerImpl implements RegisterController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("form", new UserRegisterForm("", "", "", "", 18));
        return "index";
    }

    @Override
    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("form") UserRegisterForm form, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "index";
        }

        userService.createUser(form);
        return "booking";
    }
}