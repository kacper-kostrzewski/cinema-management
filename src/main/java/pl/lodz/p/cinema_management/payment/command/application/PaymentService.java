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
        log.info("Creating new payment with PaymentNumber: " + createCommand.paymentNumber() +
                ", OrderId: " + createCommand.orderId() +
                ", UserId: " + createCommand.userId() +
                ", FilmShowId: " + createCommand.filmShowId() +
                ", SeatIds: " + createCommand.seats() +
                ", Price: " + createCommand.price());
        Payment payment = new Payment(
                PaymentNumber.of(createCommand.paymentNumber()),
                OrderId.of(createCommand.orderId()),
                UserId.of(createCommand.userId()),
                FilmShowId.of(createCommand.filmShowId()),
                SeatId.of(createCommand.seats()),
                Price.of(createCommand.price())
        );
        Payment savedPayment = paymentRepository.save(payment);
        log.info("Payment created successfully: " + savedPayment.toString());
        return savedPayment;
    }

    public Payment findByPaymentNumber(PaymentNumber paymentNumber) {
        log.info("Searching for payment with PaymentNumber: " + paymentNumber.toString());
        Payment payment = paymentRepository.findByPaymentNumber(paymentNumber)
                .orElseThrow(() -> {
                    log.warning("Payment not found with PaymentNumber: " + paymentNumber);
                    return new PaymentNotFoundException();
                });
        log.info("Payment found: " + payment.toString());
        return payment;
    }

    public void complete(String paymentNumber) {
        log.info("Completing payment with PaymentNumber: " + paymentNumber);
        Payment payment = findByPaymentNumber(PaymentNumber.of(paymentNumber));
        payment.pay();
        log.info("Payment marked as paid: " + payment.toString());

        log.info("Confirming seats for Payment: " + payment.toString());
        seatConfirmationService.confirmSeats(
                payment.getUserId().value(),
                payment.getFilmShowId().value(),
                SeatId.toStringList(payment.getSeats())
        );
        log.info("Seats confirmed for user: " + payment.getUserId().value());

        log.info("Generating ticket for Payment: " + payment.toString());
        ticketService.createTicket(
                payment.getUserId().value(),
                payment.getFilmShowId().value(),
                SeatId.toStringList(payment.getSeats()),
                payment.getPrice().value()
        );
        log.info("Ticket created successfully for user: " + payment.getUserId().value());
    }

}
