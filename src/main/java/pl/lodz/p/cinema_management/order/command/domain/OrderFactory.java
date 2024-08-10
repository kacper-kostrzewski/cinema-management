package pl.lodz.p.cinema_management.order.command.domain;

import java.util.List;

public class OrderFactory {

    public static Order createOrder(OrderNumber orderNumber, UserId userId, FilmShowId filmShowId, List<SeatId> seatIds) {
        return new Order(orderNumber, userId, filmShowId, seatIds);
    }

}
