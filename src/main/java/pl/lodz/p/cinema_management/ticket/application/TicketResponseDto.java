package pl.lodz.p.cinema_management.ticket.application;

import pl.lodz.p.cinema_management.filmshow.application.FilmShowDto;
import pl.lodz.p.cinema_management.filmshow.application.seat.SeatDto;
import pl.lodz.p.cinema_management.user.application.UserDto;

import java.time.LocalDateTime;

public record TicketResponseDto (
        Integer id,
        FilmShowDto filmShow,
        SeatDto seat,
        UserDto user,
        LocalDateTime generationTime,
        String ticketStatus
) {}
