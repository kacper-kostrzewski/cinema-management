package pl.lodz.p.cinema_management.reservation.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AdminReleasingPolicy implements ReleasingPolicy{
    @Override
    public void releaseSeats(final Reservation reservation, final Integer userId, final List<Integer> seatNumbers) {
        for (Integer seatNumber : seatNumbers) {
            Seat seat = reservation.findSeat(seatNumber);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            seat.release();
        }
    }
}
