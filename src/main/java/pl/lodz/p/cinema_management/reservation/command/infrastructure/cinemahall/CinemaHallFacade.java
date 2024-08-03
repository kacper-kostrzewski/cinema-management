package pl.lodz.p.cinema_management.reservation.command.infrastructure.cinemahall;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.reservation.command.application.CinemaHallService;


@Component
@RequiredArgsConstructor
public class CinemaHallFacade implements CinemaHallService {

    private final pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService cinemaHallService;
    private final CinemaHallMapper cinemaHallMapper;

    @Override
    public pl.lodz.p.cinema_management.reservation.command.domain.CinemaHall getCinemaHallById(Integer id) {
        CinemaHall cinemaHall = cinemaHallService.findById(id);

        return cinemaHallMapper.toReservationContext(cinemaHall);
    }

}
