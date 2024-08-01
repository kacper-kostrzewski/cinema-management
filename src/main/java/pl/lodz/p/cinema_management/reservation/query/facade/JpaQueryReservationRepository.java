package pl.lodz.p.cinema_management.reservation.query.facade;

import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaQueryReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByReservationNumber(String reservationNumber);
}
