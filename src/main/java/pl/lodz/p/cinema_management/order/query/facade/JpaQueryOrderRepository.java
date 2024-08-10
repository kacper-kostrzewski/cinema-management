package pl.lodz.p.cinema_management.order.query.facade;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.OrderNumber;

import java.util.Optional;

public interface JpaQueryOrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);
}
