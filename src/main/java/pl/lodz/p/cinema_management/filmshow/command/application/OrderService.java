package pl.lodz.p.cinema_management.filmshow.command.application;

import pl.lodz.p.cinema_management.filmshow.command.domain.OrderNumber;

import java.util.List;

public interface OrderService {
    OrderNumber createOrder(Integer userId, Integer filmShowId, List<String> seatsIds);
}
