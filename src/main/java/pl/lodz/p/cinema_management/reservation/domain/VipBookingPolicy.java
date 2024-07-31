package pl.lodz.p.cinema_management.reservation.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VipBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final Reservation reservation, final Integer userId, final List<Integer> seatNumbers) {
        if (targetAmountOfSeatsForUser(reservation, userId, seatNumbers) > 5) {
            throw new MethodNotAllowedException();
        }

        for (Integer seatNumber : seatNumbers) {
            Seat seat = reservation.findSeat(seatNumber);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            seat.takeBy(userId);
        }
    }

    private Integer targetAmountOfSeatsForUser(final Reservation reservation, final Integer userId, final List<Integer> seatNumbers) {
        Integer amountOfSeatsAlreadyTakenByUser = 0;
        for (Seat seat : reservation.getSeats()) {
            if (seat.isTakenBy(userId)) {
                amountOfSeatsAlreadyTakenByUser++;
            }
        }
        return amountOfSeatsAlreadyTakenByUser + seatNumbers.size();
    }
}
