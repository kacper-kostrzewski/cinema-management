package pl.lodz.p.cinema_management.reservation.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final Reservation reservation, final Integer userId, final List<String> seatIdentifiers) {
        if (targetAmountOfSeatsForUser(reservation, userId, seatIdentifiers) > 2) {
            throw new MethodNotAllowedException();
        }

        seatIdentifiers.stream()
                .map(reservation::findSeat)
                .forEach(seat -> {
                    if (seat == null) {
                        throw new SeatNotFoundException();
                    }
                    seat.takeBy(userId);
                });
    }

    private Integer targetAmountOfSeatsForUser(final Reservation reservation, final Integer userId, List<String> seatIdentifiers) {
        long amountOfSeatsAlreadyTakenByUser = reservation.getSeats().stream()
                .filter(seat -> seat.isTakenBy(userId))
                .count();

        return (int) amountOfSeatsAlreadyTakenByUser + seatIdentifiers.size();
    }
}
