package pl.lodz.p.cinema_management.payment.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.payment.command.domain.*;


@Service
@Transactional
@RequiredArgsConstructor
@Log
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SeatConfirmationService seatConfirmationService;
    private final TicketService ticketService;

    public Payment create(CreateCommand createCommand) {
        log.info("Creating new payment");
        return paymentRepository.save(new Payment
                (PaymentNumber.of(createCommand.paymentNumber()),
                        OrderId.of(createCommand.orderId()),
                        UserId.of(createCommand.userId()),
                        FilmShowId.of(createCommand.filmShowId()),
                        SeatId.of(createCommand.seats()),
                        Price.of(createCommand.price())
                ));
    }


    public Payment findByPaymentNumber(PaymentNumber paymentNumber) {
        return paymentRepository.findByPaymentNumber(paymentNumber)
                .orElseThrow(PaymentNotFoundException::new);
    }

    public void complete(String paymentNumber) {
        Payment payment = findByPaymentNumber(PaymentNumber.of(paymentNumber));
        payment.pay();
        seatConfirmationService.confirmSeats(
                payment.getUserId().value(),
                payment.getFilmShowId().value(),
                SeatId.toStringList(payment.getSeats())
        );

        ticketService.createTicket(
                payment.getUserId().value(),
                payment.getFilmShowId().value(),
                SeatId.toStringList(payment.getSeats())
        );

    }

}
