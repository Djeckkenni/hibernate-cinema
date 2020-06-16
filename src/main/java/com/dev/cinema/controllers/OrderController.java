package com.dev.cinema.controllers;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.OrderRequestDto;
import com.dev.cinema.model.dto.OrderResponseDto;
import com.dev.cinema.model.mapper.OrderMapper;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
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
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService,
                           ShoppingCartService shoppingCartService,
                           UserService userService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @GetMapping
    public List<OrderResponseDto> getOrdersByUser(@RequestParam Long userId) {
        User user = userService.getById(userId);
        List<Order> orders = orderService.getOrderHistory(user);
        return orders.stream()
                .map(orderMapper::getOrderDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/complete")
    public String completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        User user = userService.getById(orderRequestDto.getUserId());
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        orderService.completeOrder(shoppingCart.getTickets(), user);
        return "Order was successfully completed";
    }
}
