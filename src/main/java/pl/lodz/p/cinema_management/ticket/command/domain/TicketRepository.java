package pl.lodz.p.cinema_management.ticket.command.domain;

import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket);
    Optional<Ticket> findByTicketNumber(TicketNumber ticketNumber);
    void removeByTicketNumber(TicketNumber ticketNumber);

}
