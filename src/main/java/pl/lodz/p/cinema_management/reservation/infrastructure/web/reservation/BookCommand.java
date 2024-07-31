package pl.lodz.p.cinema_management.reservation.infrastructure.web.reservation;

import java.util.List;

public record BookCommand(
        List<Integer>seatNumbers,
        Integer userId
) {}
