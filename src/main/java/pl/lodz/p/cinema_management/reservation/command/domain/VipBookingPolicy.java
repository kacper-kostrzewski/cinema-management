package pl.lodz.p.cinema_management.reservation.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VipBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final Reservation reservation, final Integer userId, final List<String> seatsIdentifiers) {
        if (targetAmountOfSeatsForUser(reservation, userId, seatsIdentifiers) > 5) {
            throw new MethodNotAllowedException();
        }

        seatsIdentifiers.stream()
                .map(reservation::findSeat)
                .forEach(seat -> {
                    if (seat == null) {
                        throw new SeatNotFoundException();
                    }
                    seat.takeBy(userId);
                });
    }

    private Integer targetAmountOfSeatsForUser(final Reservation reservation, final Integer userId, final List<String> seatsIdentifiers) {
        long amountOfSeatsAlreadyTakenByUser = reservation.getSeats().stream()
                .filter(seat -> seat.isTakenBy(userId))
                .count();

        return (int) amountOfSeatsAlreadyTakenByUser + seatsIdentifiers.size();
    }
}
