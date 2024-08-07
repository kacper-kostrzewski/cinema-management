package pl.lodz.p.cinema_management.filmshow.command.domain;

import java.util.List;

public interface ReleasingPolicy {
    void releaseSeats(FilmShow filmShow, Integer userId, List<String> seatsIdentifiers);
}
