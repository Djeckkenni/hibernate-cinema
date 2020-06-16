package com.dev.cinema.model.dto;

import java.util.List;

public class ShoppingCartResponseDto {
    private List<TicketDto> tickets;
    private Long userId;

    public List<TicketDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
