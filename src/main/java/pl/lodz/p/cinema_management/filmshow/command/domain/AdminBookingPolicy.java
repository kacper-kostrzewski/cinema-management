package pl.lodz.p.cinema_management.filmshow.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AdminBookingPolicy implements BookingPolicy{
    @Override
    public void bookSeats(final FilmShow filmShow, final Integer userId, final List<String> seatsIdentifiers) {
        for (String identifier : seatsIdentifiers) {
            Seat seat = filmShow.findSeat(identifier);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            seat.takeBy(userId);
        }
    }
}
