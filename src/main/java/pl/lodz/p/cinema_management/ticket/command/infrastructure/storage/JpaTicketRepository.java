package pl.lodz.p.cinema_management.ticket.command.infrastructure.storage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketNumber;

import java.util.Optional;

public interface JpaTicketRepository extends JpaRepository<Ticket, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Ticket> findByTicketNumber(TicketNumber ticketNumber);
    void removeByTicketNumber(TicketNumber ticketNumber);
}
