package pl.lodz.p.cinema_management.order.command.infrastructure.authentication;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.order.command.domain.User;

@Mapper(componentModel = "spring")
public interface OrderAuthenticationMapper {

    User toOrderContext(pl.lodz.p.cinema_management.user.domain.User user);

}
