package pl.lodz.p.cinema_management.filmshow.command.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AdminReleasingPolicy implements ReleasingPolicy{
    @Override
    public void releaseSeats(final FilmShow filmShow, final Integer userId, final List<String> seatsIdentifiers) {
        for (String identifier : seatsIdentifiers) {
            Seat seat = filmShow.findSeat(identifier);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            seat.release();
        }
    }
}
