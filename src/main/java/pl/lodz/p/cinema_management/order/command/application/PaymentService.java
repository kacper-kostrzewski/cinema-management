package pl.lodz.p.cinema_management.order.command.application;

import pl.lodz.p.cinema_management.order.command.domain.Order;

public interface PaymentService {
    void processPayment(Order order);
}
