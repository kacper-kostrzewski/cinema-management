package pl.lodz.p.cinema_management.reservation.domain;

public record User(
        Integer id,
        UserRole role
) {}