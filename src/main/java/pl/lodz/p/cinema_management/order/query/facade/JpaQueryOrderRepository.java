package pl.lodz.p.cinema_management.order.query.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.OrderNumber;
import pl.lodz.p.cinema_management.order.command.domain.UserId;


import java.util.Optional;

public interface JpaQueryOrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);
    Page<Order> findAllByUserId(UserId userId, Pageable pageable);
}
