package pl.lodz.p.cinema_management.ticket.infrastructure;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.ticket.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.domain.TicketNotFoundException;
import pl.lodz.p.cinema_management.ticket.domain.TicketRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketDatabaseStorageAdapter implements TicketRepository {

     private final JpaTicketRepository jpaTicketRepository;

     public TicketDatabaseStorageAdapter(final JpaTicketRepository jpaTicketRepository) {
         this.jpaTicketRepository = jpaTicketRepository;
     }

    @Override
    public Ticket save(Ticket ticket) {
        return jpaTicketRepository.save(ticket);
    }

    @Override
    public Ticket update(Ticket ticket) {
        Optional<Ticket> ticketToUpdate = jpaTicketRepository.findById(ticket.getId());
        if (ticketToUpdate.isPresent()) {
            return jpaTicketRepository.save(ticket);
        }
        throw new TicketNotFoundException();
    }

    @Override
    public Optional<Ticket> findById(Integer id) {
        return jpaTicketRepository.findById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return jpaTicketRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public void deleteById(Integer id) {
        jpaTicketRepository.deleteById(id);
    }

}

