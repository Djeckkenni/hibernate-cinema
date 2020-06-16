package com.dev.cinema.controllers;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import com.dev.cinema.model.mapper.ShoppingCartMapper;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    private final MovieSessionService movieSessionService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartController(MovieSessionService movieSessionService,
                                  ShoppingCartService shoppingCartService,
                                  UserService userService, ShoppingCartMapper shoppingCartMapper) {
        this.movieSessionService = movieSessionService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @GetMapping("/byuser")
    public ShoppingCartResponseDto getCartByUser(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        return shoppingCartMapper.convertToShoppingCartDto(shoppingCart);
    }

    @PostMapping("/addmoviesession/{movieSessionId}")
    public String add(@PathVariable Long movieSessionId,
                      Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        MovieSession movieSession = movieSessionService.getById(movieSessionId);
        shoppingCartService.addSession(movieSession, user);
        return "Movie session was successfully added to shopping cart";
    }
}
