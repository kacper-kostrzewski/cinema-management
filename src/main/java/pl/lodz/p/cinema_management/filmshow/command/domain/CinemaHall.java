package pl.lodz.p.cinema_management.filmshow.command.domain;

import java.util.List;

public record CinemaHall(
        Integer id,
        String name,
        List<String> seatsIdentifiers
) {}
