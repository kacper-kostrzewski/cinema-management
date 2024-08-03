package pl.lodz.p.cinema_management.reservation.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AdminReleasingPolicy implements ReleasingPolicy{
    @Override
    public void releaseSeats(final Reservation reservation, final Integer userId, final List<String> seatsIdentifiers) {
        for (String identifier : seatsIdentifiers) {
            Seat seat = reservation.findSeat(identifier);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            seat.release();
        }
    }
}
