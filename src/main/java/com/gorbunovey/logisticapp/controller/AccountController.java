package com.gorbunovey.logisticapp.controller;

import com.gorbunovey.logisticapp.dto.UserDTO;
import com.gorbunovey.logisticapp.service.api.SecurityService;
import com.gorbunovey.logisticapp.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    SecurityService securityService;
    @Autowired
    UserService userService;

    @GetMapping
    public String getAccountForm(Model model){
        model.addAttribute("user", securityService.getAuthUser());
        return "auth/account";
    }

    @PostMapping
    public String setAccountForm(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult,
                                 final RedirectAttributes redirectAttributes, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("statusMsg", "Error: please, fill all fields correctly");
        }else{
            userService.updateUserAndKeepRole(userDTO);
            model.addAttribute("statusMsg", "Success. " +
                    "To apply changes, please logout and login with new data");
        }
        return "auth/account";
    }
}
