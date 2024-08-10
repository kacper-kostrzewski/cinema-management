package pl.lodz.p.cinema_management.payment.command.application;

import java.util.List;

public interface SeatConfirmationService {
    void confirmSeats(Integer userId, Integer filmShowId, List<String> seats);
}
