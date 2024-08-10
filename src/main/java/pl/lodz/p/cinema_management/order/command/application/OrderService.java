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
        return orderRepository.save(OrderFactory.createOrder(OrderNumber.of(createCommand.orderNumber()),
                UserId.of(createCommand.userId()), FilmShowId.of(createCommand.filmShowId()),
                SeatId.of(createCommand.seatsIdentifiers())));
    }

    public Order findByOrderNumber(OrderNumber orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(OrderNotFoundException::new);
    }

    public void processPayment(String orderNumber) {
        Order order = findByOrderNumber(OrderNumber.of(orderNumber));
        log.info("Processing payment for " + order);
        paymentService.createPayment(order);
    }

}
