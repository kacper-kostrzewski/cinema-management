package pl.lodz.p.cinema_management.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.order.command.application.CreateCommand;
import pl.lodz.p.cinema_management.order.command.application.OrderService;
import pl.lodz.p.cinema_management.order.query.facade.OrderDto;
import pl.lodz.p.cinema_management.order.query.facade.OrderFacade;
import pl.lodz.p.cinema_management.order.query.facade.PageOrderDto;

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

    private final OrderFacade orderFacade;

    @GetMapping(path = "/{orderNumber}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderFacade.findByOrderNumber(orderNumber));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<PageOrderDto> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderFacade.findUserOrders(pageable));
    }

    @GetMapping
    public ResponseEntity<PageOrderDto> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderFacade.findAll(pageable));
    }



}