package pl.lodz.p.cinema_management.user.api;

import java.util.List;

public record PageUserDto(
        List<UserDto> users,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {
}
