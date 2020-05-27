package com.dev.cinema;

import com.dev.cinema.dao.TicketDao;
import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.inject.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        CinemaHallService cinemaHallService = (CinemaHallService) INJECTOR
                .getInstance(CinemaHallService.class);
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        LocalDate date = LocalDate.of(2020, 5, 21);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.of(2020, 5, 21),
                LocalTime.now()));
        MovieSessionService movieSessionService = (MovieSessionService)
                INJECTOR.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movieSession.getId(),
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

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setMovieSession(movieSession);
        TicketDao ticketDao = (TicketDao) INJECTOR.getInstance(TicketDao.class);
        ticketDao.add(ticket);
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);

        ShoppingCartService cartService =
                (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
        cartService.registerNewShoppingCart(user);
        ShoppingCart cart = cartService.getByUser(user);
        cart.setTickets(ticketList);
        System.out.println("CartID: " + cart.getId());
        cartService.addSession(movieSession, user);
        ShoppingCart byUser = cartService.getByUser(user);
        System.out.println(byUser.toString());

        OrderService orderService =
                (OrderService) INJECTOR.getInstance(OrderService.class);
        orderService.completeOrder(cart.getTickets(), user);
        List<Order> orderHistory = orderService.getOrderHistory(user);
        System.out.println(orderHistory.toString());
    }
}
