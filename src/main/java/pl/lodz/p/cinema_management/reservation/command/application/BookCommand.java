package pl.lodz.p.cinema_management.reservation.command.application;

import java.util.List;

public record BookCommand(
        List<Integer>seatNumbers,
        Integer userId
) {}
