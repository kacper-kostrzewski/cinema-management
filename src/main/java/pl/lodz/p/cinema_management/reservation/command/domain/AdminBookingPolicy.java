package pl.lodz.p.cinema_management.reservation.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AdminBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final Reservation reservation, final Integer userId, final List<String> seatsIdentifiers) {
        for (String identifier : seatsIdentifiers) {
            Seat seat = reservation.findSeat(identifier);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            seat.takeBy(userId);
        }
    }
}
