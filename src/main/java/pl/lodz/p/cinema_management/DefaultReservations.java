package pl.lodz.p.cinema_management;

import org.springframework.core.annotation.Order;
import pl.lodz.p.cinema_management.filmshow.command.application.CreateCommand;
import pl.lodz.p.cinema_management.filmshow.command.application.FilmShowService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

@Component
@Log
@Order(4)
public class DefaultReservations implements CommandLineRunner {

    private final FilmShowService filmShowService;

    public DefaultReservations(FilmShowService filmShowService) {
        this.filmShowService = filmShowService;
    }

    @Override
    public void run(String... args) {
        try {
            createReservation(new CreateCommand("FS1", 1, 1, LocalDateTime.of(2024, Month.JUNE, 12, 14, 30)));
            createReservation(new CreateCommand("FS2", 2, 1, LocalDateTime.of(2024, Month.JUNE, 10, 10, 15)));
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void createReservation(CreateCommand createCommand) throws Exception {
        filmShowService.create(createCommand);
    }
}
