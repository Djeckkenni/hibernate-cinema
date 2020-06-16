package com.dev.cinema.model.mapper;

import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper {
    private final CinemaHallService cinemaHallService;
    private final MovieService movieService;

    public MovieSessionMapper(CinemaHallService cinemaHallService, MovieService movieService) {
        this.cinemaHallService = cinemaHallService;
        this.movieService = movieService;
    }

    public MovieSession getMovieSession(MovieSessionRequestDto movieSessionRequestDto) {
        MovieSession movieSession = new MovieSession();
        Movie movie = movieService.getById(movieSessionRequestDto.getMovieId());
        movieSession.setMovie(movie);
        CinemaHall cinemaHall = cinemaHallService.getById(movieSessionRequestDto.getCinemaHallId());
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setShowTime(LocalDateTime.parse(movieSessionRequestDto.getShowTime()));
        return movieSession;
    }

    public MovieSessionResponseDto getMovieSessionDto(MovieSession movieSession) {
        MovieSessionResponseDto movieSessionResponseDto = new MovieSessionResponseDto();
        movieSessionResponseDto.setMovieTitle(movieSession.getMovie().getTitle());
        movieSessionResponseDto.setHallDescription(movieSession.getCinemaHall().getDescription());
        movieSessionResponseDto.setShowTime(movieSession.getShowTime().toString());
        return movieSessionResponseDto;
    }
}
