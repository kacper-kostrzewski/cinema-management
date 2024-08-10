package pl.lodz.p.cinema_management.filmshow.command.application;

import java.util.List;

public record GenerateOrderCommand(
        String orderNumber,
        Integer userId,
        List<String> seatsIdentifiers
) {}
