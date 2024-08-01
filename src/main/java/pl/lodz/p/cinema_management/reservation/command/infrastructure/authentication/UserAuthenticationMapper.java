package pl.lodz.p.cinema_management.reservation.command.infrastructure.authentication;

import pl.lodz.p.cinema_management.reservation.command.domain.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    User toReservationContext(pl.lodz.p.cinema_management.user.domain.User user);

}
