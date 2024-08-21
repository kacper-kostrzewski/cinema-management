package pl.lodz.p.cinema_management.order.command.infrastructure.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.order.command.application.PaymentService;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.SeatId;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log
public class PaymentServiceAdapter implements PaymentService {

    private final pl.lodz.p.cinema_management.payment.command.application.PaymentService paymentService;

    private final AtomicInteger paymentCounter = new AtomicInteger(0);

    @Override
    public void createPayment(Order order) {
        log.info(String.format("Starting payment processing for order %d.", order.getId()));

        int paymentNumber = paymentCounter.incrementAndGet();
        String paymentNumberStr = "PAYMENT-" + paymentNumber;

        List<String> seatIdentifiers = order.getSeats().stream()
                .map(SeatId::asString)
                .collect(Collectors.toList());

        try {
            pl.lodz.p.cinema_management.payment.command.application.CreateCommand createCommand =
                new pl.lodz.p.cinema_management.payment.command.application.CreateCommand(
                    paymentNumberStr,
                    order.getId(),
                    order.getUserId().value(),
                    order.getFilmShowId().value(),
                    seatIdentifiers,
                    order.getPrice().value()
            );

            paymentService.create(createCommand);
            log.info(String.format("Successfully processed payment: %s for order %d.", paymentNumberStr, order.getId()));
        } catch (Exception e) {
            log.severe(String.format("Error processing payment %s for order %d: %s", paymentNumberStr, order.getId(), e.getMessage()));
        }

        log.info(String.format("Finished payment processing for order %d.", order.getId()));
    }
}
