package pl.lodz.p.cinema_management.order.command.domain;

public record User(
        Integer id,
        UserRole role
) {}