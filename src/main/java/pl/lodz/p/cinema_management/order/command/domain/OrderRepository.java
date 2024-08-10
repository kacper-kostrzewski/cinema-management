package pl.lodz.p.cinema_management.order.command.domain;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByOrderNumber(OrderNumber orderNumber);

    void deleteByOrderNumber(OrderNumber orderNumber);

}
