package pl.lodz.p.cinema_management.filmshow.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VipBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final FilmShow filmShow, final Integer userId, final List<String> seatsIdentifiers) {
        if (targetAmountOfSeatsForUser(filmShow, userId, seatsIdentifiers) > 5) {
            throw new MethodNotAllowedException();
        }

        seatsIdentifiers.stream()
                .map(filmShow::findSeat)
                .forEach(seat -> {
                    if (seat == null) {
                        throw new SeatNotFoundException();
                    }
                    seat.takeBy(userId);
                });
    }

    private Integer targetAmountOfSeatsForUser(final FilmShow filmShow, final Integer userId, final List<String> seatsIdentifiers) {
        long amountOfSeatsAlreadyTakenByUser = filmShow.getSeats().stream()
                .filter(seat -> seat.isTakenBy(userId))
                .count();

        return (int) amountOfSeatsAlreadyTakenByUser + seatsIdentifiers.size();
    }
}
