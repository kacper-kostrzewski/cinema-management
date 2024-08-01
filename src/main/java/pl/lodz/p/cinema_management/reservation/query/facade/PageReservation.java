package pl.lodz.p.cinema_management.reservation.query.facade;

import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageReservation implements Serializable {

    List<Reservation> reservations;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}