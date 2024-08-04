package pl.lodz.p.cinema_management.reservation.command.application;

import lombok.extern.java.Log;
import pl.lodz.p.cinema_management.reservation.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AuthenticationService authenticationService;
    private final CinemaHallService cinemaHallService;
    private final AvailabilityService availabilityService;

    public Reservation create(final CreateUsingCinemaHallCommand command) {
        CinemaHall cinemaHall = cinemaHallService.getCinemaHallById(command.cinemaHallId());

        availabilityService.lockTimeFrame(cinemaHall.name(), command.reservationNumber(), LocalDateTime.now(), 120);

        return reservationRepository.save(ReservationFactory.createReservation(command.reservationNumber(), cinemaHall));
    }

    public void removeByReservationNumber(String reservationNumber) {
        Reservation reservation = findByReservationNumber(reservationNumber);        reservationRepository.remove(reservation.getId());
    }

    public Reservation findByReservationNumber(String reservationNumber) {

        final Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(ReservationNotFoundException::new);
        return reservation;
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