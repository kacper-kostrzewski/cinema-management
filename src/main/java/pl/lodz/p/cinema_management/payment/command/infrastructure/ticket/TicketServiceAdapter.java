package pl.lodz.p.cinema_management.payment.command.infrastructure.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.payment.command.application.TicketService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Log
public class TicketServiceAdapter implements TicketService {

    private final pl.lodz.p.cinema_management.ticket.command.application.TicketService ticketService;

    private final AtomicInteger ticketCounter = new AtomicInteger(0);

    @Override
    public void createTicket(Integer userId, Integer filmShowId, List<String> seats) {
        log.info(String.format("Starting ticket creation for user %d for film show %d.", userId, filmShowId));

        for (String seat : seats) {
            int ticketNumber = ticketCounter.incrementAndGet();
            String ticketNumberStr = "TICKET-" + ticketNumber;

            pl.lodz.p.cinema_management.ticket.command.application.CreateCommand createCommand =
                    new pl.lodz.p.cinema_management.ticket.command.application.CreateCommand(ticketNumberStr, filmShowId, userId, seat);

            try {
                ticketService.create(createCommand);
                log.info(String.format("Successfully created ticket: %s for user %d for film show %d with seat %s.", ticketNumberStr, userId, filmShowId, seat));
            } catch (Exception e) {
                log.severe(String.format("Error creating ticket %s for user %d for film show %d with seat %s: %s", ticketNumberStr, userId, filmShowId, seat, e.getMessage()));
            }
        }

        log.info(String.format("Finished ticket creation for user %d for film show %d.", userId, filmShowId));
    }

}
