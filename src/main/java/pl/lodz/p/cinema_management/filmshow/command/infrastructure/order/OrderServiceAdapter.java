package pl.lodz.p.cinema_management.filmshow.command.infrastructure.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.filmshow.command.application.OrderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderServiceAdapter implements OrderService {

    private final pl.lodz.p.cinema_management.order.command.application.OrderService orderService;

    @Override
    public void createOrder(String orderNumber, Integer userId, Integer filmShowId, List<String> seatsIds) {
        orderService.create(new pl.lodz.p.cinema_management.order.command.application.CreateCommand(orderNumber, userId, filmShowId, seatsIds));
    }

}
