package com.gorbunovey.logisticapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping(value = "/index")
    public String getIndexPage(Model model){
        return "index";
    }

    @GetMapping(value = "/about")
    public String getAboutPage(Model model){
        return "about";
    }

//    @GetMapping("/default")
//    public String defaultAfterLogin(HttpServletRequest request) {
//        if (request.isUserInRole("ROLE_ADMIN")) {
//            return "redirect:/accounts";
//        }
//        return "redirect:/index";
//    }

}
