package com.dev.cinema.controllers;

import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    public MovieSessionController(CinemaHallService cinemaHallService,
                                  MovieSessionService movieSessionService,
                                  MovieService movieService) {
        this.cinemaHallService = cinemaHallService;
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getMovieSessions(@RequestParam Long movieId,
                                                          @RequestParam LocalDate date) {
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(movieId, date);
        return availableSessions.stream()
                .map(this::getMovieSessionDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public String addMovieSession(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(getMovieSession(movieSessionRequestDto));
        return "MovieSession was successful added";
    }

    private MovieSession getMovieSession(MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        Movie movie = movieService.getById(movieSessionRequestDto.getMovieId());
        movieSession.setMovie(movie);
        CinemaHall cinemaHall = cinemaHallService.getById(movieSessionRequestDto.getCinemaHallId());
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.parse(movieSessionRequestDto.getShowTime()));
        return movieSession;
    }

    private MovieSessionResponseDto getMovieSessionDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setMovieTitle(movieSession.getMovie().getTitle());
        movieSessionResponseDto.setHallDescription(movieSession.getCinemaHall().getDescription());
        movieSessionResponseDto.setShowTime(movieSession.getShowTime().toString());
        return movieSessionResponseDto;
    }
}
