package pl.lodz.p.cinema_management.payment.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.payment.command.application.PaymentService;


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

}
