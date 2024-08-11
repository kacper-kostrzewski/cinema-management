package pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.authentication;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.ticket.command.domain.User;

@Mapper(componentModel = "spring")
public interface TicketAuthenticationMapper {

    User toTicketContext(pl.lodz.p.cinema_management.user.domain.User user);

}
