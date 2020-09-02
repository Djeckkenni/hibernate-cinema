package com.dev.cinema.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    @GetMapping
    public String sayWelcome() {
        return "Welcome to Cinema Booking System!";
    }
}
