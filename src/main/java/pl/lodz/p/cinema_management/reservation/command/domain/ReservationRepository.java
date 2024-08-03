package pl.lodz.p.cinema_management.reservation.command.domain;

import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void remove(Integer id);

    Optional<Reservation> findByReservationNumber(String reservationNumber);

}