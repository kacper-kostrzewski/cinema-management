package pl.lodz.p.cinema_management.ticket.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowNotFoundException;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.domain.seat.*;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserNotFoundException;
import pl.lodz.p.cinema_management.user.domain.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FilmShowService filmShowService;
    private final SeatService seatService;
    private final UserService userService;

    public Optional<Ticket> getTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }

    public Ticket createTicket(Ticket ticket) {
        FilmShow filmShow = filmShowService.getFilmShowById(ticket.getFilmShow().getId()).orElseThrow(FilmShowNotFoundException::new);
        ticket.setFilmShow(filmShow);

        if (filmShow.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot create a ticket for a film show that has already taken place");
        }

        Seat seat = seatService.findAndReserveSeat(filmShow.getId(), ticket.getSeat().getRowNumber(), ticket.getSeat().getSeatNumber());
        ticket.setSeat(seat);

        User user = userService.getUserById(ticket.getUser().getId())
                .orElseThrow(UserNotFoundException::new);
        ticket.setUser(user);

        ticket.setGenerationTime(LocalDateTime.now());
        ticket.setTicketStatus(TicketStatus.VALID);

        return ticketRepository.save(ticket);
    }

    public void invalidateTicket(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        if (ticket.getTicketStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Cannot invalidate a ticket that has been canceled");
        }

        if (ticket.getTicketStatus() == TicketStatus.INVALID) {
            throw new IllegalStateException("Ticket is already invalidated");
        }

        if (ticket.getFilmShow().getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot invalidate a ticket for a film show that has already taken place");
        }

        ticket.setTicketStatus(TicketStatus.INVALID);
        ticketRepository.save(ticket);
        seatService.releaseSeat(ticket.getSeat());
    }

    public void markTicketAsUsed(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);

        if (ticket.getTicketStatus() == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Cannot use a ticket that has been canceled");
        }

        if (ticket.getTicketStatus() == TicketStatus.INVALID) {
            throw new IllegalStateException("Cannot use a ticket that is invalid");
        }

        if (ticket.getTicketStatus() == TicketStatus.USED) {
            throw new IllegalStateException("Ticket is already used");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime filmShowStartTime = ticket.getFilmShow().getStartTime();
        LocalDateTime allowedUseTime = filmShowStartTime.minusMinutes(30);

        if (now.isAfter(allowedUseTime)) {
            throw new IllegalStateException("Cannot use a ticket before the film show has started");
        }

        ticket.setTicketStatus(TicketStatus.USED);
        ticketRepository.save(ticket);
    }

}
