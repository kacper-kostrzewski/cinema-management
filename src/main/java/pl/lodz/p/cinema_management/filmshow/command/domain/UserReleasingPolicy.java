package pl.lodz.p.cinema_management.filmshow.command.domain;

import java.util.List;

public class UserReleasingPolicy implements ReleasingPolicy{
    @Override
    public void releaseSeats(final FilmShow filmShow, final Integer userId, final List<String> seatsIdentifiers) {
        for (String identifier : seatsIdentifiers) {
            Seat seat = filmShow.findSeat(identifier);
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
