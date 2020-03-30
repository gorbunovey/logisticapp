package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login")
    public String getLoginForm(@RequestParam(name = "error", required = false) Boolean error, Model model){
        if(Boolean.TRUE.equals(error)){
            model.addAttribute("error", true);
        }
        return "auth/login";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationForm(Model model){
        model.addAttribute("user", new UserDTO());
        return "auth/registration";
    }

    @PostMapping(value = "/registration")
    public String registerNewUser(@ModelAttribute("user") @Valid UserDTO user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Failure. Couldn't add new user. Cause - invalid data");
            return "auth/registration";
        } else {
            userService.registerNewUser(user);
        }
        return "redirect:/login";
    }
}
