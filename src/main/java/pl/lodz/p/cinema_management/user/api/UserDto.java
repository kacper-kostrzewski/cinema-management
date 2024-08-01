package pl.lodz.p.cinema_management.user.api;

public record UserDto(
        Integer id,
        String email,
        String name,
        String password,
        String role
) {}
