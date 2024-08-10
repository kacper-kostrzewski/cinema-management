package pl.lodz.p.cinema_management.payment.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.PaymentNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class PaymentFacade {

    private final JpaQueryPaymentRepository jpaQueryPaymentRepository;
    private final PaymentDtoMapper paymentDtoMapper;
    private final PagePaymentDtoMapper pagePaymentDtoMapper;

    public PaymentDto findByPaymentNumber(final String paymentNumber) {
        final Optional<Payment> maybePayment = jpaQueryPaymentRepository.findByPaymentNumber(PaymentNumber.of(paymentNumber));
        return paymentDtoMapper.toDto(maybePayment.orElseThrow(PaymentNotFoundException::new));
    }

    public PagePaymentDto findAll(final Pageable pageable) {
        Page<Payment> pageOfPaymentsEntity = jpaQueryPaymentRepository.findAll(pageable);
        List<Payment> paymentsOnCurrentPage = new ArrayList<>(pageOfPaymentsEntity.getContent());

        final PagePayment pagePayment = new PagePayment(
                paymentsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfPaymentsEntity.getTotalPages(),
                pageOfPaymentsEntity.getTotalElements()
        );
        return pagePaymentDtoMapper.toPageDto(pagePayment);
    }

}
