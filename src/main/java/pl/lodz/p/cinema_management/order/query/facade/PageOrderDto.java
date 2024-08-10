package pl.lodz.p.cinema_management.order.query.facade;

import java.util.List;


public record PageOrderDto(
    List<OrderDto> orders,
    Integer currentPage,
    Integer totalPages,
    Long totalElements
) {
}
