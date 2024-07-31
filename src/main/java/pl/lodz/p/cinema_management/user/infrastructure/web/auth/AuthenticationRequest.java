package pl.lodz.p.cinema_management.user.infrastructure.web.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
