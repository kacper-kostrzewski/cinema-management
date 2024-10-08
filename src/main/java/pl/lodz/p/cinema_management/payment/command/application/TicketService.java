package pl.lodz.p.cinema_management.payment.command.application;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {
    void createTicket(Integer userId, Integer filmShowId, List<String> seats, BigDecimal totalPrice);
}
