package com.dev.cinema.controllers;

import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.dto.CinemaHallResponseDto;
import com.dev.cinema.model.mapper.CinemaHallMapper;
import com.dev.cinema.service.CinemaHallService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinemahalls")
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final CinemaHallMapper cinemaHallMapper;

    @Autowired
    public CinemaHallController(CinemaHallService cinemaHallService,
                                CinemaHallMapper cinemaHallMapper) {
        this.cinemaHallService = cinemaHallService;
        this.cinemaHallMapper = cinemaHallMapper;
    }

    @GetMapping
    List<CinemaHallResponseDto> getAllCinemaHalls() {
        List<CinemaHall> halls = cinemaHallService.getAll();
        return halls.stream().map(cinemaHallMapper::getCinemaHallDto).collect(Collectors.toList());
    }

    @PostMapping("/addcinemahall")
    public String addCinemaHall(@RequestBody @Valid CinemaHallResponseDto cinemaHallResponseDto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(cinemaHallResponseDto.getCapacity());
        cinemaHall.setDescription(cinemaHallResponseDto.getDescription());
        cinemaHallService.add(cinemaHall);
        return "Cinema hall was added!";
    }
}
