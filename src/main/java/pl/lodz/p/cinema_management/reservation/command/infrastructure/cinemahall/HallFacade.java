package pl.lodz.p.cinema_management.reservation.command.infrastructure.cinemahall;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService;
import pl.lodz.p.cinema_management.reservation.command.application.HallService;


@Component
@RequiredArgsConstructor
public class HallFacade implements HallService {

    private final CinemaHallService cinemaHallService;
    private final HallMapper hallMapper;

    @Override
    public pl.lodz.p.cinema_management.reservation.command.domain.CinemaHall getCinemaHallById(Integer id) {
        CinemaHall cinemaHall = cinemaHallService.findById(id);

        return hallMapper.toReservationContext(cinemaHall);
    }

}
