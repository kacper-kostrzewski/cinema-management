package pl.lodz.p.cinema_management.user.infrastructure.web.auth;


import pl.lodz.p.cinema_management.user.infrastructure.web.user.UserDto;

public record AuthenticationResponse(
        String token,
        UserDto userDto
) {
}
