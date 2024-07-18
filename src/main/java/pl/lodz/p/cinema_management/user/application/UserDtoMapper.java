package pl.lodz.p.cinema_management.user.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.cinema_management.user.domain.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(target="password", constant = "######")
    UserDto toDto(User domain);

    User toDomain(UserDto dto);
}
