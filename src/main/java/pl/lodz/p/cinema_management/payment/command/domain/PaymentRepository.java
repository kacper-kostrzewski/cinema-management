package pl.lodz.p.cinema_management.payment.command.domain;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByPaymentNumber(PaymentNumber paymentNumber);

    void removeByPaymentNumber(PaymentNumber paymentNumber);

}
