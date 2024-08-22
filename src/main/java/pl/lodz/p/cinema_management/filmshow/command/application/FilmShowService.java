package pl.lodz.p.cinema_management.filmshow.command.application;

import lombok.extern.java.Log;
import pl.lodz.p.cinema_management.filmshow.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class FilmShowService {

    private final FilmShowRepository filmShowRepository;
    private final AuthenticationService authenticationService;
    private final CinemaHallService cinemaHallService;
    private final FilmService filmService;
    private final AvailabilityService availabilityService;
    private final OrderService orderService;

    public FilmShow create(final CreateCommand createCommand) {
        log.info("Creating FilmShow with Film ID: " + createCommand.filmId() + " and CinemaHall ID: " + createCommand.cinemaHallId());
        CinemaHall cinemaHall = cinemaHallService.getCinemaHallById(createCommand.cinemaHallId());
        Film film = filmService.getFilmById(createCommand.filmId());
        try {
            log.info("Attempting to lock CinemaHall: " + cinemaHall.name() + " for FilmShowNumber: " + createCommand.filmShowNumber());
            availabilityService.lockCinemaHall(cinemaHall.name(), createCommand.filmShowNumber(), createCommand.filmShowDateTime(), film.duration());
        } catch (RuntimeException e) {
            log.warning("Failed to lock CinemaHall: " + cinemaHall.name() + " for FilmShowNumber: " + createCommand.filmShowNumber() + " due to: " + e.getMessage());
            throw new FilmShowAlreadyExistsException();
        }
        FilmShow filmShow = filmShowRepository.save(FilmShowFactory.createFilmShow(createCommand.filmShowNumber(), cinemaHall, film, createCommand.filmShowDateTime()));
        log.info("FilmShow created successfully: " + filmShow.toString());
        return filmShow;
    }

    public FilmShow findByFilmShowId(Integer id) {
        log.info("Searching for FilmShow with ID: " + id);
        FilmShow filmShow = filmShowRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("FilmShow not found with ID: " + id);
                    return new FilmShowNotFoundException();
                });
        log.info("FilmShow found: " + filmShow.toString());
        return filmShow;
    }

    public FilmShow findByFilmShowNumber(String filmShowNumber) {
        log.info("Searching for FilmShow with Number: " + filmShowNumber);
        FilmShow filmShow = filmShowRepository.findByFilmShowNumber(filmShowNumber)
                .orElseThrow(() -> {
                    log.warning("FilmShow not found with Number: " + filmShowNumber);
                    return new FilmShowNotFoundException();
                });
        log.info("FilmShow found: " + filmShow.toString());
        return filmShow;
    }

    public void removeByFilmShowNumber(String filmShowNumber) {
        log.info("Attempting to remove FilmShow with Number: " + filmShowNumber);
        FilmShow filmShow = findByFilmShowNumber(filmShowNumber);
        filmShowRepository.remove(filmShow.getId());
        log.info("FilmShow removed successfully with ID: " + filmShow.getId());
    }

    public void bookSeats(String filmShowNumber, BookCommand bookCommand) {
        log.info("Attempting to book seats for FilmShowNumber: " + filmShowNumber + " for user: " + bookCommand.userId());
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = FilmShowFactory.prepareReservationForUser(findByFilmShowNumber(filmShowNumber), user);

        if (bookCommand.userId() == null) {
            filmShow.bookSeats(user.id(), bookCommand.seatsIdentifiers());
            log.info("Seats " + bookCommand.seatsIdentifiers() + " temporarily booked for user: " + user.id());
        } else {
            if (user.role() != UserRole.ADMIN) {
                log.warning("Unauthorized attempt to book seats by user: " + user.id() + " with role: " + user.role());
                throw new MethodNotAllowedException();
            }
            filmShow.bookSeats(bookCommand.userId(), bookCommand.seatsIdentifiers());
            log.info("Seats " + bookCommand.seatsIdentifiers() + " booked by admin for user: " + bookCommand.userId());
        }
    }

    public void releaseSeats(String filmShowNumber, final ReleaseCommand releaseCommand) {
        log.info("Releasing seats for FilmShowNumber: " + filmShowNumber);
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = FilmShowFactory.prepareReservationForUser(findByFilmShowNumber(filmShowNumber), user);
        filmShow.releaseSeats(user.id(), releaseCommand.seatsIdentifiers());
        log.info("Seats " + releaseCommand.seatsIdentifiers() + " released for user: " + user.id());
    }

    public OrderNumber generateOrder(String filmShowNumber) {
        log.info("Generating order for FilmShowNumber: " + filmShowNumber);
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = findByFilmShowNumber(filmShowNumber);

        List<String> reservedSeats = filmShow.getReservedSeatsForUser(user.id());
        if (reservedSeats.isEmpty()) {
            log.warning("No seats reserved for user: " + user.id());
            throw new RuntimeException("No seats reserved for user: " + user.id());
        }

        filmShow.validateSeatsForUser(user.id(), reservedSeats);
        OrderNumber orderNumber = OrderNumber.of(orderService.createOrder(user.id(), filmShow.getId(), reservedSeats).orderNumber());
        log.info("Order generated successfully for user: " + user.id() + " with seats: " + reservedSeats);

        return orderNumber;
    }
}