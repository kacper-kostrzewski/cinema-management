package pl.lodz.p.cinema_management.filmshow.command.domain;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Log
public class UserBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final FilmShow filmShow, final Integer userId, final List<String> seatIdentifiers) {
        if (targetAmountOfSeatsForUser(filmShow, userId, seatIdentifiers) > 2) {
            throw new MethodNotAllowedException();
        }

        seatIdentifiers.stream()
                .map(filmShow::findSeat)
                .forEach(seat -> {
                    if (seat == null) {
                        throw new SeatNotFoundException();
                    }
                    seat.takeBy(userId);
                });
    }

    private Integer targetAmountOfSeatsForUser(final FilmShow filmShow, final Integer userId, List<String> seatIdentifiers) {
        long amountOfSeatsAlreadyTakenByUser = filmShow.getSeats().stream()
                .filter(seat -> seat.isTakenBy(userId) && seat.temporarilyBookedTo.isAfter(LocalDateTime.now()))
                .count();

        return (int) amountOfSeatsAlreadyTakenByUser + seatIdentifiers.size();
    }
}
