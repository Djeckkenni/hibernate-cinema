package com.dev.cinema.controllers;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.model.mapper.MovieSessionMapper;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviesessions")
public class MovieSessionController {
    private final CinemaHallService cinemaHallService;
    private final MovieSessionService movieSessionService;
    private final MovieService movieService;
    private final MovieSessionMapper movieSessionMapper;

    @Autowired
    public MovieSessionController(CinemaHallService cinemaHallService,
                                  MovieSessionService movieSessionService,
                                  MovieService movieService,
                                  MovieSessionMapper movieSessionMapper) {
        this.cinemaHallService = cinemaHallService;
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getMovieSessions(@RequestParam Long movieId,
                                                          @RequestParam LocalDate date) {
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(movieId, date);
        return availableSessions.stream()
                .map(movieSessionMapper::getMovieSessionDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/addmoviesession")
    public String addMovieSession(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(movieSessionMapper.getMovieSession(movieSessionRequestDto));
        return "MovieSession was successful added";
    }
}
