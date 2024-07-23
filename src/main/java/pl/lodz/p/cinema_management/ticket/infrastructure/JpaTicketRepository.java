package pl.lodz.p.cinema_management.ticket.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.ticket.domain.Ticket;

public interface JpaTicketRepository extends JpaRepository<Ticket, Integer> {
}
