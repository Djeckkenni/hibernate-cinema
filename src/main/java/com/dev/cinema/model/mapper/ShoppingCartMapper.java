package com.dev.cinema.model.mapper;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import com.dev.cinema.model.dto.TicketDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    public ShoppingCartResponseDto convertToShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setUserId(shoppingCart.getUser().getId());
        List<TicketDto> tickets = shoppingCart.getTickets().stream()
                .map(this::convertToTicketDto)
                .collect(Collectors.toList());
        shoppingCartResponseDto.setTickets(tickets);
        return shoppingCartResponseDto;
    }

    public TicketDto convertToTicketDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setMovieTitle(ticket.getMovieSession().getMovie().getTitle());
        ticketDto.setCinemaHallId(ticket.getMovieSession().getCinemaHall().getId());
        ticketDto.setShowTime(ticket.getMovieSession().getShowTime().toString());
        return ticketDto;
    }
}
