package pl.lodz.p.cinema_management.reservation.domain;

import java.util.List;

public class UserReleasingPolicy implements ReleasingPolicy{
    @Override
    public void releaseSeats(final Reservation reservation, final Integer userId, final List<Integer> seatNumbers) {
        for (Integer seatNumber : seatNumbers) {
            Seat seat = reservation.findSeat(seatNumber);
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
