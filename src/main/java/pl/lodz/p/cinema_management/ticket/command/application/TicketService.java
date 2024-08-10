package pl.lodz.p.cinema_management.ticket.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.ticket.command.domain.*;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket create(CreateCommand createCommand) {
        log.info("Attempting to create a new ticket with details: " + createCommand);

        Ticket ticket = new Ticket(
                TicketNumber.of(createCommand.ticketNumber()),
                FilmShowId.of(createCommand.filmShowId()),
                UserId.of(createCommand.userId()),
                SeatId.of(createCommand.seatId())
        );
        log.info("Created ticket entity: " + ticket);

        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Saved ticket entity: " + savedTicket);

        return savedTicket;
    }

}
