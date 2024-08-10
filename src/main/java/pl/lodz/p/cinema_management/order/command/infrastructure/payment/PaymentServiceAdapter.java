package pl.lodz.p.cinema_management.order.command.infrastructure.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.order.command.application.PaymentService;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.SeatId;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentServiceAdapter implements PaymentService {

    private final pl.lodz.p.cinema_management.payment.command.application.PaymentService paymentService;

    @Override
    public void processPayment(Order order) {

        List<String> seatIdentifiers = order.getSeats().stream()
                .map(SeatId::asString)
                .collect(Collectors.toList());

        paymentService.create(new pl.lodz.p.cinema_management.payment.command.application.CreateCommand(
                "PAY-" + order.getId(),
            order.getId(),
            order.getUserId().value(),
            order.getFilmShowId().value(),
            seatIdentifiers,
            order.getPrice().value()
        ));
    }

}
