package pl.lodz.p.cinema_management.auth.api;

public record AuthenticationResponse(
        String token,
        AuthUserDto userDto
) {
}
