package pl.lodz.p.cinema_management.ticket.command.domain;

import pl.lodz.p.cinema_management.filmshow.command.domain.UserRole;

public record User(
        Integer id,
        UserRole role
) {}