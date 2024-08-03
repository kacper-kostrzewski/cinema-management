package pl.lodz.p.cinema_management;


import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.Seat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCinemaHallFactory {

    public static CinemaHall createCinemaHall() {
        return new CinemaHall(
                "Main Hall",
                Stream.of("A1", "A2", "A3", "B1", "B2", "B3")
                        .map(text -> new Seat(null, text))
                        .collect(Collectors.toList())
        );
    }
}
