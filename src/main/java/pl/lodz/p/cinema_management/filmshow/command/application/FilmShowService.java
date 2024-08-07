package pl.lodz.p.cinema_management.filmshow.command.application;

import lombok.extern.java.Log;
import pl.lodz.p.cinema_management.filmshow.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public FilmShow create(final CreateCommand createCommand) {
        CinemaHall cinemaHall = cinemaHallService.getCinemaHallById(createCommand.cinemaHallId());
        Film film = filmService.getFilmById(createCommand.filmId());
        try {
        availabilityService.lockTimeFrame(cinemaHall.name(), createCommand.filmShowNumber(), createCommand.filmShowDateTime(), film.duration());
        } catch (RuntimeException e) {
            throw new FilmShowAlreadyExistsException();
        }
        return filmShowRepository.save(FilmShowFactory.createFilmShow(createCommand.filmShowNumber(), cinemaHall, film, createCommand.filmShowDateTime()));
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


    }

    public void releaseSeats(String filmShowNumber, final ReleaseCommand releaseCommand) {
        User user = authenticationService.getLoggedInUser();
        FilmShow filmShow = FilmShowFactory.prepareReservationForUser(findByFilmShowNumber(filmShowNumber), user);
        filmShow.releaseSeats(user.id(), releaseCommand.seatsIdentifiers());
    }

}