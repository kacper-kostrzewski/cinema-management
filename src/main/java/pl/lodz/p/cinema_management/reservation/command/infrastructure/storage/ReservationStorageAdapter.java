package pl.lodz.p.cinema_management.reservation.command.infrastructure.storage;


import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import pl.lodz.p.cinema_management.reservation.command.domain.ReservationAlreadyExistsException;
import pl.lodz.p.cinema_management.reservation.command.domain.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@RequiredArgsConstructor
@Log
public
class ReservationStorageAdapter implements ReservationRepository {

    private final JpaReservationRepository reservationRepository;

    @Override
    public Reservation save(final Reservation reservation) {
        try {
            Reservation saved = reservationRepository.save(reservation);
            log.info("Saved entity " + saved);
            return saved;
        } catch (DataIntegrityViolationException ex) {
            log.warning("Reservation with number " + reservation.getReservationNumber() + " already exits in db");
            throw new ReservationAlreadyExistsException();
        }
    }

    @Override
    public void remove(final Integer id) {
        reservationRepository.findById(id).ifPresent(userEntity -> reservationRepository.deleteById(id));
    }

    @Override
    public Optional<Reservation> findByReservationNumber(final String reservationNumber) {
        return reservationRepository.findByReservationNumber(reservationNumber);
    }

}
