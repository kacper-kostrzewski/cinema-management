package pl.lodz.p.cinema_management.reservation.command.domain;

import java.util.List;

public class UserReleasingPolicy implements ReleasingPolicy{
    @Override
    public void releaseSeats(final Reservation reservation, final Integer userId, final List<String> seatsIdentifiers) {
        for (String identifier : seatsIdentifiers) {
            Seat seat = reservation.findSeat(identifier);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            if (!seat.getTakenBy().equals(userId)) {
                throw new MethodNotAllowedException();
            }
            seat.release();
        }
    }
}
