package pl.lodz.p.cinema_management.payment.command.application;

import java.util.List;

public record PayCommand(
        Integer userId,
        String filmShowNumber,
        List<String> seatIdentifiers
) {
}
