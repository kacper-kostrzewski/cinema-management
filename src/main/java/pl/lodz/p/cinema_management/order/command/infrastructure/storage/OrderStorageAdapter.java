package pl.lodz.p.cinema_management.order.command.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.OrderAlreadyExistsException;
import pl.lodz.p.cinema_management.order.command.domain.OrderNumber;
import pl.lodz.p.cinema_management.order.command.domain.OrderRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log
public class OrderStorageAdapter implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public Order save(Order order) {
        try {
            Order saved = jpaOrderRepository.save(order);
            log.info("Saved entity: " + saved);
            return saved;
        } catch (DataIntegrityViolationException ex) {
            log.warning("Order for User" + order.getUserId() + " already exists in db");
            throw new OrderAlreadyExistsException();
        }
    }

    @Override
    public Optional<Order> findByOrderNumber(OrderNumber orderNumber) {
        return jpaOrderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public void deleteByOrderNumber(OrderNumber orderNumber) {
        jpaOrderRepository.deleteByOrderNumber(orderNumber);
    }

}
