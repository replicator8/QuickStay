package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.quickstay_contracts.controllers.LoginController;
import com.example.quickstay_contracts.viewmodel.BaseViewModel;
import com.example.quickstay_contracts.viewmodel.UserAuthForm;
import com.example.quickstay_contracts.viewmodel.UserRegisterForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginControllerImpl implements LoginController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String signIn(Model model) {
        model.addAttribute("model", new BaseViewModel("Login"));
        model.addAttribute("loginForm", new UserAuthForm("", ""));
        return "login";
    }

    @Override
    @PostMapping("/auth")
    public String signIn(@Valid @ModelAttribute("loginForm") UserAuthForm form, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", form);
            return "login";
        }
        model.addAttribute("model", new BaseViewModel("Login"));

        User user = userService.findByUserName(form.userName());
        if (user != null) {
            if (user.getPassword().equals(form.password())) {
                String uuid = userService.findByUserName(form.userName()).getId();
                model.addAttribute("userUUID", uuid);
                return "redirect:/bookings/getHotels";
            }
        }
        return "login";
    }

}
