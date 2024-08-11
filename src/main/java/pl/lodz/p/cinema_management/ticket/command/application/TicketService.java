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

        Ticket savedTicket = ticketRepository.save(
                TicketFactory.createTicket(TicketNumber.of(createCommand.ticketNumber()),
                        UserId.of(createCommand.userId()),
                        FilmName.of(filmShow.filmName()),
                        CinemaHallName.of(filmShow.cinemaHallName()),
                        FilmShowDateTime.of(filmShow.filmShowDateTime()),
                        SeatId.of(createCommand.seatId()),
                        Price.of(createCommand.price()),
                        GenerationTime.of(LocalDateTime.now()),
                        TicketStatus.VALID
                )
        );
        log.info("Saved ticket entity: " + savedTicket);

        return savedTicket;
    }


    public void markTicketAsUsed(TicketNumber ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(TicketNotFoundException::new);
        ticket.markAsUsed();
        ticketRepository.save(ticket);
    }


    public void cancelTicket(TicketNumber ticketNumber) {
        Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(TicketNotFoundException::new);
        ticket.cancel();
        ticketRepository.save(ticket);
    }

}
