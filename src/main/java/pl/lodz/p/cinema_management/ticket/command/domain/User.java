package pl.lodz.p.cinema_management.ticket.command.domain;

public record User(
        Integer id,
        UserRole role
) {}