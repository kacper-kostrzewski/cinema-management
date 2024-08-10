package pl.lodz.p.cinema_management.order.command.infrastructure.storage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.OrderNumber;

import java.util.Optional;

public interface JpaOrderRepository extends JpaRepository<Order, Integer> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);

    void deleteByOrderNumber(OrderNumber orderNumber);

}
