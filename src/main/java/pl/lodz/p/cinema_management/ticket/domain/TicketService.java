package pl.lodz.p.cinema_management.ticket.domain;

import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowNotFoundException;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.domain.seat.*;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserNotFoundException;
import pl.lodz.p.cinema_management.user.domain.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final FilmShowRepository filmShowRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, FilmShowRepository filmShowRepository, SeatRepository seatRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.filmShowRepository = filmShowRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        FilmShow filmShow = findFilmShow(ticket.getFilmShow().getId());
        ticket.setFilmShow(filmShow);

        Seat seat = findAndReserveSeat(ticket.getSeat().getId(), filmShow.getId());
        ticket.setSeat(seat);

        User user = findUser(ticket.getUser().getId());
        ticket.setUser(user);

        ticket.setGenerationTime(LocalDateTime.now());
        ticket.setTicketStatus(TicketStatus.VALID);

        return ticketRepository.save(ticket);
    }

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

    private FilmShow findFilmShow(Integer filmShowId) {
        return filmShowRepository.findById(filmShowId)
                .orElseThrow(FilmShowNotFoundException::new);
    }

    private Seat findAndReserveSeat(Integer seatId, Integer filmShowId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(SeatNotFoundException::new);

        if (!seat.getFilmShow().getId().equals(filmShowId)) {
            throw new IllegalArgumentException("Seat does not belong to the specified film show");
        }

        if (seat.getSeatStatus() == SeatStatus.OCCUPIED) {
            throw new IllegalStateException("Seat is already occupied");
        }

        seat.setSeatStatus(SeatStatus.OCCUPIED);
        seatRepository.save(seat);

        return seat;
    }

    private User findUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}
