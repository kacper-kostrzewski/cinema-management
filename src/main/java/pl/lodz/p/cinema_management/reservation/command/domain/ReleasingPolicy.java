package pl.lodz.p.cinema_management.reservation.command.domain;

import java.util.List;

public interface ReleasingPolicy {
    void releaseSeats(Reservation reservation, Integer userId, List<Integer> seatNumbers);
}
