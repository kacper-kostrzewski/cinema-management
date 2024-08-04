package pl.lodz.p.cinema_management.cinemahall.application;

import pl.lodz.p.cinema_management.cinemahall.domain.Seat;

import java.util.List;

public record CinemaHallDto(
        Integer id,
        String name,
        List<Seat> seats
) {}
