package pl.lodz.p.cinema_management.auth.api;

public record AuthenticationRequest(
        String username,
        String password
) {
}
