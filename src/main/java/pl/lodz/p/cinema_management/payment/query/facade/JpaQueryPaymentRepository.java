package pl.lodz.p.cinema_management.payment.query.facade;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.PaymentNumber;

import java.util.Optional;

public interface JpaQueryPaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByPaymentNumber(PaymentNumber paymentNumber);
}
