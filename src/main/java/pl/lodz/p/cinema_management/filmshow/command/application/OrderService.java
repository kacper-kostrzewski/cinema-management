package pl.lodz.p.cinema_management.filmshow.command.application;

import java.util.List;

public interface OrderService {
    void createOrder(String orderNumber, Integer userId, Integer filmShowId, List<String> seatsIds);
}
