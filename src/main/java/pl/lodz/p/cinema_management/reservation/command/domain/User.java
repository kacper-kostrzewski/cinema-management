package pl.lodz.p.cinema_management.reservation.command.domain;

public record User(
        Integer id,
        UserRole role
) {}