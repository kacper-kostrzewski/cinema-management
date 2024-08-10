package pl.lodz.p.cinema_management.payment.command.application;

import java.math.BigDecimal;
import java.util.List;

public record CreateCommand(
        String paymentNumber,
        Integer orderId,
        Integer userId,
        Integer filmShowId,
        List<String> seats,
        BigDecimal price
) {
}
