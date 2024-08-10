package pl.lodz.p.cinema_management.payment.facade;

import java.util.List;


public record PagePaymentDto(
    List<PaymentDto> payments,
    Integer currentPage,
    Integer totalPages,
    Long totalElements
) {
}
