package pl.lodz.p.cinema_management.payment.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.payment.command.application.PaymentService;
import pl.lodz.p.cinema_management.payment.query.facade.PagePaymentDto;
import pl.lodz.p.cinema_management.payment.query.facade.PaymentDto;
import pl.lodz.p.cinema_management.payment.query.facade.PaymentFacade;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/payment",
        produces = "application/json",
        consumes = "application/json"
)
class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{paymentNumber}/complete")
    public ResponseEntity<Void> complete(@PathVariable String paymentNumber) {
        paymentService.complete(paymentNumber);
        return ResponseEntity.ok().build();
    }

    private final PaymentFacade paymentFacade;

    @GetMapping(path = "/{paymentNumber}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable String paymentNumber) {
        return ResponseEntity.ok(paymentFacade.findByPaymentNumber(paymentNumber));
    }

    @GetMapping
    public ResponseEntity<PagePaymentDto> getPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(paymentFacade.findAll(pageable));
    }


}
