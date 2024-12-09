package com.example.demo.controllers;

import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.RegisterController;
import com.example.quickstay_contracts.viewmodel.BaseViewModel;
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
        model.addAttribute("model", new BaseViewModel("Register"));
        model.addAttribute("registerForm", new UserRegisterForm("", "", "", "", 18));
        return "index";
    }

    @Override
    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("registerForm") UserRegisterForm form, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerForm", form);
            return "index";
        }
        model.addAttribute("title", "Booking");

        userService.createUser(form);
        String uuid = userService.findByUserName(form.userName()).getId();
        model.addAttribute("userUUID", uuid);

        return "redirect:/bookings/getHotels";
    }

    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}