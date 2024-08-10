package pl.lodz.p.cinema_management.payment.command.infrastructure.storage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.PaymentNumber;

import java.util.Optional;

public interface JpaPaymentRepository extends JpaRepository<Payment, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Payment> findByPaymentNumber(PaymentNumber paymentNumber);
    void removeByPaymentNumber(PaymentNumber paymentNumber);
}
