package com.dev.cinema;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.inject.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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
        User user = new User();
        user.setEmail("bob");
        user.setPassword("1");
        UserService userService = (UserService)
                INJECTOR.getInstance(UserService.class);
        userService.add(user);
        Optional<User> user1 = userService.findByEmail("bob");
        System.out.println(user1);
        AuthenticationService authenticationService =
                (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);
        authenticationService.register("email", "password");
        try {
            authenticationService.login("email", "password");
            System.out.println("Good");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Ooops!");
        }
        ShoppingCartService cartService =
                (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
        cartService.registerNewShoppingCart(user);
        ShoppingCart cart = cartService.getByUser(user);
        System.out.println("CartID: " + cart.getId());
        cartService.addSession(firstMovieSession, user);
        ShoppingCart byUser = cartService.getByUser(user);
        System.out.println(byUser.toString());
    }
}
