package pl.lodz.p.cinema_management.payment.command.infrastructure.filmshow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.payment.command.application.SeatConfirmationService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatConfirmationServiceAdapter implements SeatConfirmationService {

    private final pl.lodz.p.cinema_management.filmshow.command.application.SeatConfirmationService seatConfirmationService;

    @Override
    public void confirmSeats(Integer userId, Integer filmShowId, List<String> seats) {
        seatConfirmationService.confirmSeats(filmShowId, new pl.lodz.p.cinema_management.filmshow.command.application.ConfirmCommand(userId, seats));
    }


}
