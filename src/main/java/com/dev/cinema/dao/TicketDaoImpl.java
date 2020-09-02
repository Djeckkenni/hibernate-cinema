package com.dev.cinema.dao;

import com.dev.cinema.model.Ticket;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements TicketDao {

    @Autowired
    public TicketDaoImpl(SessionFactory factory) {
        super(factory);
    }

    @Override
    public Ticket add(Ticket ticket) {
        ticket = super.add(ticket);
        return ticket;
    }

    @Override
    public Ticket getById(Long id) {
        return super.getById(id, Ticket.class);
    }
}
