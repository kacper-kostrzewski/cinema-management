package pl.lodz.p.cinema_management;

import org.springframework.core.annotation.Order;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.cinemahall.domain.Seat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log
@Order(2)
public class DefaultCinemaHalls implements CommandLineRunner {

    private final CinemaHallService cinemaHallService;

    public DefaultCinemaHalls(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }

    private final CinemaHall hall1 = new CinemaHall(
            "Hall 1",
            Stream.of("A1", "A2", "A3").map(text -> new Seat(null, text)).collect(Collectors.toList())
    );

    private final CinemaHall hall2 = new CinemaHall(
            "Hall 2",
            Stream.of("A1", "A2", "B1", "B2").map(text -> new Seat(null, text)).collect(Collectors.toList())
    );

    private final CinemaHall hall3 = new CinemaHall(
            "Main Hall",
            Stream.of("A1", "A2", "B1", "B2", "B2", "B3", "C1", "C2", "C3").map(text -> new Seat(null, text)).collect(Collectors.toList())
    );

    @Override
    public void run(String... args) {
        try {
            addCinemaHall(hall1);
            addCinemaHall(hall2);
            addCinemaHall(hall3);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void addCinemaHall(CinemaHall cinemaHall) {
        cinemaHallService.save(cinemaHall);
    }
}
