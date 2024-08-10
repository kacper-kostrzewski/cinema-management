package pl.lodz.p.cinema_management.payment.command.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.PaymentNumber;
import pl.lodz.p.cinema_management.payment.command.domain.PaymentRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Log
public class PaymentStorageAdapter implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;

    @Override
    public Payment save(final Payment payment) {
        try {
            Payment saved = jpaPaymentRepository.save(payment);
            log.info("Saved entity " + saved);
            return saved;
        } catch (Exception e) {
            log.warning("Payment with number " + payment.getPaymentNumber() + " already exists in db");
            throw e;
        }
    }

    @Override
    public void removeByPaymentNumber(final PaymentNumber paymentNumber) {
        jpaPaymentRepository.removeByPaymentNumber(paymentNumber);
    }

    @Override
    public Optional<Payment> findByPaymentNumber(final PaymentNumber paymentNumber) {
        return jpaPaymentRepository.findByPaymentNumber(paymentNumber);
    }

}
