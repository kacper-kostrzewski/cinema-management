package pl.lodz.p.cinema_management.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.order.command.application.CreateCommand;
import pl.lodz.p.cinema_management.order.command.application.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/order",
        produces = "application/json",
        consumes = "application/json"
)
class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody CreateCommand createCommand) {
        orderService.create(createCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderNumber}/process-payment")
    public ResponseEntity<Void> processPayment(@PathVariable String orderNumber) {
        orderService.processPayment(orderNumber);
        return ResponseEntity.ok().build();
    }



}