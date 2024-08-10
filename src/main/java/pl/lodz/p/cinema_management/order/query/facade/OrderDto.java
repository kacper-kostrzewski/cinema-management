package pl.lodz.p.cinema_management.order.query.facade;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(
        Integer id,
        String orderNumber,
        Integer userId,
        Integer filmShowId,
        BigDecimal price,
        List<String> seats,
        Integer version
) {
}
