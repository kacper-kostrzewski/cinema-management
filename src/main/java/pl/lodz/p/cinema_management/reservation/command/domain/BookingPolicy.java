package pl.lodz.p.cinema_management.reservation.command.domain;

import java.util.List;

public interface BookingPolicy {
    void bookSeats(Reservation reservation, Integer userId, List<String> seatsIdentifiers);
}
