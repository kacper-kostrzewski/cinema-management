package pl.lodz.p.cinema_management.order.command.application;

import java.util.List;

public record ConfirmCommand(
        String orderNumber,
        Integer userId,
        Integer filmShowId,
        List<String> seatsIdentifiers
) {
}
