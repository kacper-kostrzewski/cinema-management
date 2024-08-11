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
        CinemaHall cinemaHall = cinemaHallService.getCinemaHallById(createCommand.cinemaHallId());
        Film film = filmService.getFilmById(createCommand.filmId());
        try {
        availabilityService.lockCinemaHall(cinemaHall.name(), createCommand.filmShowNumber(), createCommand.filmShowDateTime(), film.duration());
        } catch (RuntimeException e) {
            throw new FilmShowAlreadyExistsException();
        }
        return filmShowRepository.save(FilmShowFactory.createFilmShow(createCommand.filmShowNumber(), cinemaHall, film, createCommand.filmShowDateTime()));
    }

    public FilmShow findByFilmShowId(Integer id) {
        return filmShowRepository.findById(id)
                .orElseThrow(FilmShowNotFoundException::new);
    }

    public FilmShow findByFilmShowNumber(String filmShowNumber) {
        return filmShowRepository.findByFilmShowNumber(filmShowNumber)
                .orElseThrow(FilmShowNotFoundException::new);
    }

    public void removeByFilmShowNumber(String filmShowNumber) {
        FilmShow filmShow = findByFilmShowNumber(filmShowNumber);
        filmShowRepository.remove(filmShow.getId());
    }

    public void bookSeats(String filmShowNumber, BookCommand bookCommand) {
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = FilmShowFactory.prepareReservationForUser(findByFilmShowNumber(filmShowNumber), user);

        if (bookCommand.userId() == null) {
            filmShow.bookSeats(user.id(), bookCommand.seatsIdentifiers());
        } else {
            if (user.role() != UserRole.ADMIN) {
                throw new MethodNotAllowedException();
            }
            filmShow.bookSeats(bookCommand.userId(), bookCommand.seatsIdentifiers());
        }

        log.info("Temporarily booked seats: " + bookCommand.seatsIdentifiers() + " for user: " + user.id());

    }


    public void releaseSeats(String filmShowNumber, final ReleaseCommand releaseCommand) {
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = FilmShowFactory.prepareReservationForUser(findByFilmShowNumber(filmShowNumber), user);
        filmShow.releaseSeats(user.id(), releaseCommand.seatsIdentifiers());
    }

    public void generateOrder(String filmShowNumber) {
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = findByFilmShowNumber(filmShowNumber);

        List<String> reservedSeats = filmShow.getReservedSeatsForUser(user.id());
        if (reservedSeats.isEmpty()) {
            throw new RuntimeException("No seats reserved for user: " + user.id());
        }

        filmShow.validateSeatsForUser(user.id(), reservedSeats);

        orderService.createOrder(user.id(), filmShow.getId(), reservedSeats);

        log.info("Order generated successfully for user: " + user.id() + " with seats: " + reservedSeats);
    }




}