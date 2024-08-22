package pl.lodz.p.cinema_management.order.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.order.command.domain.*;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public Order create(CreateCommand createCommand) {
        log.info("Creating order with OrderNumber: " + createCommand.orderNumber() +
                ", UserId: " + createCommand.userId() +
                ", FilmShowId: " + createCommand.filmShowId() +
                ", SeatIds: " + createCommand.seatsIdentifiers());
        Order order = OrderFactory.createOrder(OrderNumber.of(createCommand.orderNumber()),
                UserId.of(createCommand.userId()), FilmShowId.of(createCommand.filmShowId()),
                SeatId.of(createCommand.seatsIdentifiers()));
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully: " + savedOrder.toString());
        return savedOrder;
    }

    public Order findByOrderNumber(OrderNumber orderNumber) {
        log.info("Searching for order with OrderNumber: " + orderNumber.toString());
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> {
                    log.warning("Order not found with OrderNumber: " + orderNumber);
                    return new OrderNotFoundException();
                });
        log.info("Order found: " + order.toString());
        return order;
    }

    public void processPayment(String orderNumber) {
        log.info("Attempting to process payment for OrderNumber: " + orderNumber);
        Order order = findByOrderNumber(OrderNumber.of(orderNumber));
        log.info("Processing payment for order: " + order.toString());
        paymentService.createPayment(order);
        log.info("Payment processed successfully for order: " + order.toString());
    }
}
