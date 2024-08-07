package pl.lodz.p.cinema_management.filmshow.query.facade;

import java.time.LocalDateTime;
import java.util.List;

public record FilmShowDto(
        String filmShowNumber,
        String cinemaHallName,
        String filmName,
        LocalDateTime filmShowDateTime,
        List<SeatDto>seats,
        Integer version
) {}
