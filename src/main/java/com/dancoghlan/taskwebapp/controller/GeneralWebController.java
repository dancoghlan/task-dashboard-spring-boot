package com.dancoghlan.taskwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class GeneralWebController {

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @RequestMapping("/logout")
    public String logoutPage(Model model) {
        return "logout";
    }

    @RequestMapping("/denied")
    public String accessDeniedPage(Model model) {
        return "access-denied";
    }

}
