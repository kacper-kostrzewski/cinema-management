package pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketNumber;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log
public class TicketStorageAdapter implements TicketRepository {

    private final JpaTicketRepository jpaTicketRepository;

    @Override
    public Ticket save(final Ticket ticket) {
        try {
            Ticket saved = jpaTicketRepository.save(ticket);
            log.info("Saved entity " + saved);
            return saved;
        } catch (Exception e) {
            log.warning("Ticket with number " + ticket.getTicketNumber() + " already exists in db");
            throw e;
        }
    }

    @Override
    public void removeByTicketNumber(final TicketNumber ticketNumber) {
        jpaTicketRepository.removeByTicketNumber(ticketNumber);
    }

    @Override
    public Optional<Ticket> findByTicketNumber(final TicketNumber ticketNumber) {
        return jpaTicketRepository.findByTicketNumber(ticketNumber);
    }

}
