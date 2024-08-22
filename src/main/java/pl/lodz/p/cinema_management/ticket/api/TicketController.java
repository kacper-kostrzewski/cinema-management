package pl.lodz.p.cinema_management.ticket.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.ticket.command.application.TicketService;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketNotFoundException;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketNumber;
import pl.lodz.p.cinema_management.ticket.query.facade.PageTicketDto;
import pl.lodz.p.cinema_management.ticket.query.facade.TicketDto;
import pl.lodz.p.cinema_management.ticket.query.facade.TicketFacade;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/ticket",
        produces = "application/json",
        consumes = "application/json"
)
class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{ticketNumber}/use")
    public ResponseEntity<Void> markTicketAsUsed(@PathVariable String ticketNumber) {
        ticketService.markTicketAsUsed(TicketNumber.of(ticketNumber));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ticketNumber}/cancel")
    public ResponseEntity<Void> cancelTicket(@PathVariable String ticketNumber) {
        ticketService.cancelTicket(TicketNumber.of(ticketNumber));
        return ResponseEntity.ok().build();
    }

    private final TicketFacade ticketFacade;

    @GetMapping(path = "/{ticketNumber}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable String ticketNumber) {
        return ResponseEntity.ok(ticketFacade.findByTicketNumber(ticketNumber));
    }

    @GetMapping("/my-tickets")
    public ResponseEntity<PageTicketDto> getMyTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ticketFacade.findUserTickets(pageable));
    }

    @GetMapping
    public ResponseEntity<PageTicketDto> getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ticketFacade.findAll(pageable));
    }
}
