package pl.lodz.p.cinema_management.ticket.query.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketNumber;
import pl.lodz.p.cinema_management.ticket.command.domain.UserId;

import java.util.Optional;

public interface JpaQueryTicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<Ticket> findByTicketNumber(TicketNumber ticketNumber);
    Page<Ticket> findAllByUserId(UserId userId, Pageable pageable);
}
