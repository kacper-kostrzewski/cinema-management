package pl.lodz.p.cinema_management.filmshow.command.infrastructure.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.filmshow.command.application.OrderService;
import pl.lodz.p.cinema_management.filmshow.command.domain.OrderNumber;
import pl.lodz.p.cinema_management.order.command.application.CreateCommand;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Log
public class OrderServiceAdapter implements OrderService {

    private final pl.lodz.p.cinema_management.order.command.application.OrderService orderService;

    private final AtomicInteger orderCounter = new AtomicInteger(0);

    @Override
    public OrderNumber createOrder(Integer userId, Integer filmShowId, List<String> seatsIds) {
        log.info(String.format("Starting order creation for user %d for film show %d.", userId, filmShowId));

        int orderNumber = orderCounter.incrementAndGet();
        String orderNumberStr = "ORDER-" + orderNumber;

        try {
            CreateCommand createCommand = new CreateCommand(orderNumberStr, userId, filmShowId, seatsIds);
            orderService.create(createCommand);
            log.info(String.format("Successfully created order: %s for user %d for film show %d.", orderNumberStr, userId, filmShowId));
        } catch (Exception e) {
            log.severe(String.format("Error creating order %s for user %d for film show %d: %s", orderNumberStr, userId, filmShowId, e.getMessage()));
        }

        log.info(String.format("Finished order creation for user %d for film show %d.", userId, filmShowId));

        return OrderNumber.of(orderNumberStr);
    }
}
