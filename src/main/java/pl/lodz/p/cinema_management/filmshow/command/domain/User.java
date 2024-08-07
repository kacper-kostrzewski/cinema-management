package pl.lodz.p.cinema_management.filmshow.command.domain;

public record User(
        Integer id,
        UserRole role
) {}