package pl.lodz.p.cinema_management.filmshow.command.infrastructure.authentication;

import pl.lodz.p.cinema_management.filmshow.command.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    User toReservationContext(pl.lodz.p.cinema_management.user.domain.User user);

}
