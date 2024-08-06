package pl.lodz.p.cinema_management.reservation.command.application;

import lombok.extern.java.Log;
import pl.lodz.p.cinema_management.reservation.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AuthenticationService authenticationService;
    private final CinemaHallService cinemaHallService;
    private final FilmService filmService;
    private final AvailabilityService availabilityService;

    public Reservation create(final CreateCommand createCommand) {
        CinemaHall cinemaHall = cinemaHallService.getCinemaHallById(createCommand.cinemaHallId());
        Film film = filmService.getFilmById(createCommand.filmId());
        try {
        availabilityService.lockTimeFrame(cinemaHall.name(), createCommand.reservationNumber(), createCommand.reservationDateTime(), film.duration());
        } catch (RuntimeException e) {
            throw new ReservationAlreadyExistsException();
        }
        return reservationRepository.save(ReservationFactory.createReservation(createCommand.reservationNumber(), cinemaHall, film, createCommand.reservationDateTime()));
    }

    public void removeByReservationNumber(String reservationNumber) {
        Reservation reservation = findByReservationNumber(reservationNumber);
        reservationRepository.remove(reservation.getId());
    }

    public Reservation findByReservationNumber(String reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(ReservationNotFoundException::new);
    }

    public void bookSeats(String reservationNumber, BookCommand bookCommand) {
        User user = authenticationService.getLoggedInUser();
        Reservation reservation = ReservationFactory.prepareReservationForUser(findByReservationNumber(reservationNumber), user);

        if (bookCommand.userId() == null) {
            reservation.bookSeats(user.id(), bookCommand.seatsIdentifiers());
        } else {
            if (user.role() != UserRole.ADMIN) {
                throw new MethodNotAllowedException();
            }

            reservation.bookSeats(bookCommand.userId(), bookCommand.seatsIdentifiers());
        }


    }

    public void releaseSeats(String reservationNumber, final ReleaseCommand releaseCommand) {
        User user = authenticationService.getLoggedInUser();
        Reservation reservation = ReservationFactory.prepareReservationForUser(findByReservationNumber(reservationNumber), user);
        reservation.releaseSeats(user.id(), releaseCommand.seatsIdentifiers());
    }

}