package pl.lodz.p.cinema_management.reservation.command.infrastructure.storage;

import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Reservation> findByReservationNumber(String reservationNumber);
}
