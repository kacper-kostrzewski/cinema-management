package pl.lodz.p.cinema_management.auth.api;

import pl.lodz.p.cinema_management.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthUserDtoMapper {

    @Mapping(target="password", constant = "######")
    AuthUserDto toDto(User domain);

}