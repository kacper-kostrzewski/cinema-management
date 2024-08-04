package pl.lodz.p.cinema_management.reservation.command.domain;

import java.util.List;

public record CinemaHall(
        Integer id,
        String name,
        List<String> seatsIdentifiers
) {}
