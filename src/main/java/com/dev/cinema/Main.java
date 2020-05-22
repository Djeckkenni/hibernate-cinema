package com.dev.cinema;

import com.dev.cinema.inject.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        Movie firstMovie = new Movie();
        firstMovie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(firstMovie);
        movieService.getAll().forEach(System.out::println);

        CinemaHall firstCinemaHall = new CinemaHall();
        firstCinemaHall.setCapacity(100);
        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(firstCinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession firstMovieSession = new MovieSession();
        firstMovieSession.setCinemaHall(firstCinemaHall);
        firstMovieSession.setMovie(firstMovie);
        LocalDate date = LocalDate.of(2020, 5, 21);
        firstMovieSession.setShowTime(LocalDateTime.of(LocalDate.of(2020, 5, 21),
                LocalTime.now()));
        MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(firstMovieSession);
        movieSessionService.findAvailableSessions(firstMovieSession.getId(),
                date).forEach(System.out::println);
    }
}
