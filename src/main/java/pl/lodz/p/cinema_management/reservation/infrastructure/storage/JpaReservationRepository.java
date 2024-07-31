package pl.lodz.p.cinema_management.reservation.infrastructure.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.reservation.domain.Reservation;

import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Integer> {
//    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Reservation> findByReservationNumber(String reservationNumber);
}
