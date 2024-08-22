package pl.lodz.p.cinema_management.ticket.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.ticket.command.domain.*;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FilmShowDetailsService filmShowDetailsService;

    public Ticket create(CreateCommand createCommand) {
        log.info("Attempting to create a new ticket with details: " + createCommand);

        FilmShow filmShow = filmShowDetailsService.getFilmShowById(createCommand.filmShowId());
        log.info("Retrieved FilmShow details for FilmShow ID: " + createCommand.filmShowId());

        Ticket ticket = TicketFactory.createTicket(
                TicketNumber.of(createCommand.ticketNumber()),
                UserId.of(createCommand.userId()),
                FilmName.of(filmShow.filmName()),
                CinemaHallName.of(filmShow.cinemaHallName()),
                FilmShowDateTime.of(filmShow.filmShowDateTime()),
                SeatId.of(createCommand.seatId()),
                Price.of(createCommand.price()),
                GenerationTime.of(LocalDateTime.now()),
                TicketStatus.VALID
        );

        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Ticket created and saved successfully: " + savedTicket);

        return savedTicket;
    }

    public void markTicketAsUsed(TicketNumber ticketNumber) {
        log.info("Marking ticket as used for TicketNumber: " + ticketNumber.toString());

        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> {
                    log.warning("Ticket not found with TicketNumber: " + ticketNumber);
                    return new TicketNotFoundException();
                });

        ticket.markAsUsed();
        log.info("Ticket marked as used successfully: " + ticket);
    }

    public void cancelTicket(TicketNumber ticketNumber) {
        log.info("Cancelling ticket with TicketNumber: " + ticketNumber.toString());

        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> {
                    log.warning("Ticket not found with TicketNumber: " + ticketNumber);
                    return new TicketNotFoundException();
                });

        ticket.cancel();
        log.info("Ticket cancelled successfully: " + ticket);
    }

}
