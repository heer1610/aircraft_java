package com.student.aircraft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/aircraft")
    public String aircraftPage() {
        return "aircraft";
    }

    @GetMapping("/maintenance")
    public String maintenancePage() {
        return "maintenance";
    }

    @GetMapping("/airlines")
    public String airlinesPage() {
        return "airlines";
    }
}
