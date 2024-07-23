package pl.lodz.p.cinema_management.ticket.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository {
    Ticket save(Ticket ticket);
    Ticket update(Ticket ticket);
    Optional<Ticket> findById(Integer id);
    List<Ticket> findAll();
    void deleteById(Integer id);
}
