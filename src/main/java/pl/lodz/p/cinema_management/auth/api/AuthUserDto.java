package pl.lodz.p.cinema_management.auth.api;

public record AuthUserDto(
        Integer id,
        String email,
        String name,
        String password,
        String role
) {}
