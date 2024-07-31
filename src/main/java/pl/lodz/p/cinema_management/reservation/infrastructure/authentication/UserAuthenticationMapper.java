package pl.lodz.p.cinema_management.reservation.infrastructure.authentication;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.reservation.domain.User;


@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    User toReservationContext(pl.lodz.p.cinema_management.user.domain.User user);

}
