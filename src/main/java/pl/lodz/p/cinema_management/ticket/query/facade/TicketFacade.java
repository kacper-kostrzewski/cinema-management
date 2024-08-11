package pl.lodz.p.cinema_management.ticket.query.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.ticket.command.application.AuthenticationService;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketNumber;
import pl.lodz.p.cinema_management.ticket.command.domain.User;
import pl.lodz.p.cinema_management.ticket.command.domain.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class TicketFacade {

    private final JpaQueryTicketRepository jpaQueryTicketRepository;
    private final TicketDtoMapper ticketDtoMapper;
    private final PageTicketDtoMapper pageTicketDtoMapper;
    private final AuthenticationService authenticationService;

    public TicketDto findByTicketNumber(final String ticketNumber) {
        final Optional<Ticket> maybeTicket = jpaQueryTicketRepository.findByTicketNumber(TicketNumber.of(ticketNumber));
        return ticketDtoMapper.toDto(maybeTicket.orElseThrow(TicketNotFoundException::new));
    }

    public PageTicketDto findUserTickets(Pageable pageable) {
        User user = authenticationService.getLoggedInUser();

        if (user == null) {
            throw new RuntimeException("User is not authenticated");
        }

        Page<Ticket> pageOfTicketsEntity = jpaQueryTicketRepository.findAllByUserId(UserId.of(user.id()), pageable);
        List<Ticket> ticketsOnCurrentPage = new ArrayList<>(pageOfTicketsEntity.getContent());

        final PageTicket pageTicket = new PageTicket(
                ticketsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfTicketsEntity.getTotalPages(),
                pageOfTicketsEntity.getTotalElements()
        );
        return pageTicketDtoMapper.toPageDto(pageTicket);
    }

    public PageTicketDto findAll(final Pageable pageable) {
        Page<Ticket> pageOfTicketsEntity = jpaQueryTicketRepository.findAll(pageable);
        List<Ticket> ticketsOnCurrentPage = new ArrayList<>(pageOfTicketsEntity.getContent());

        final PageTicket pageTicket = new PageTicket(
                ticketsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfTicketsEntity.getTotalPages(),
                pageOfTicketsEntity.getTotalElements()
        );
        return pageTicketDtoMapper.toPageDto(pageTicket);
    }

}
