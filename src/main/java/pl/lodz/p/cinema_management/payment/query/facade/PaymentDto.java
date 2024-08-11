package pl.lodz.p.cinema_management.payment.query.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PaymentDto(
        Integer id,
        String paymentNumber,
        Integer orderId,
        Integer userId,
        Integer filmShowId,
        BigDecimal price,
        List<String> seats,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        String status,
        Integer version
) {
}
