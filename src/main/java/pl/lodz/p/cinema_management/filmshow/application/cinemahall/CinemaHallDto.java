package pl.lodz.p.cinema_management.filmshow.application.cinemahall;

import java.util.List;

public record CinemaHallDto(
        Integer id,
        String name,
        Integer capacity,
        List<SeatDto> seats
) {
    public CinemaHallDto(Integer id, String name, Integer capacity) {
        this(id, name, capacity, List.of());
    }
}

