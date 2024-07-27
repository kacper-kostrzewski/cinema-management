package pl.lodz.p.cinema_management.ticket.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.ticket.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.domain.TicketNotFoundException;
import pl.lodz.p.cinema_management.ticket.domain.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketDtoMapper ticketDtoMapper;

    public TicketController(TicketService ticketService, TicketDtoMapper ticketDtoMapper) {
        this.ticketService = ticketService;
        this.ticketDtoMapper = ticketDtoMapper;
    }

    @PostMapping
    public ResponseEntity<String> generateTicket(@RequestBody TicketRequestDto ticketRequestDto) {
        try {
            Ticket ticket = ticketDtoMapper.toDomain(ticketRequestDto);
            ticketService.createTicket(ticket);
            return new ResponseEntity<>("Ticket created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid ticket request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Cannot create ticket: " + e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while creating the ticket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<SimplifiedTicketDto>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets().stream().map(ticketDtoMapper::toSimplifiedTicketDto).toList());
    }

    @PostMapping("/invalidate/{id}")
    public ResponseEntity<String> invalidateTicket(@PathVariable Integer id) {
        try {
            ticketService.invalidateTicket(id);
            return new ResponseEntity<>("Ticket invalidated successfully", HttpStatus.OK);
        } catch (TicketNotFoundException e) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Cannot invalidate ticket: " + e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while invalidating the ticket: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/use/{id}")
    public ResponseEntity<String> markTicketAsUsed(@PathVariable Integer id) {
        try {
            ticketService.markTicketAsUsed(id);
            return new ResponseEntity<>("Ticket marked as used successfully", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>("Marking as used failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while marking the ticket as used: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
