package pl.lodz.p.cinema_management.filmshow.command.application;

import java.util.List;

public record ConfirmCommand(
        Integer userId,
        List<String> seatsIdentifiers
) {}
