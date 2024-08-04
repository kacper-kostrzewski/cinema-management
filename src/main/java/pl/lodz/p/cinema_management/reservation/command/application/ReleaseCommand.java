package pl.lodz.p.cinema_management.reservation.command.application;

import java.util.List;

public record ReleaseCommand(
        List<String> seatsIdentifiers,
        Integer userId
) {}
