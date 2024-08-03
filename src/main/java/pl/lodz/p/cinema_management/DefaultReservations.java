package pl.lodz.p.cinema_management;

import org.springframework.core.annotation.Order;
import pl.lodz.p.cinema_management.reservation.command.application.CreateUsingCinemaHallCommand;
import pl.lodz.p.cinema_management.reservation.command.application.ReservationService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log
@Order(3)
public class DefaultReservations implements CommandLineRunner {

    private final ReservationService reservationService;

    public DefaultReservations(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) {
        try {
            createReservationWithAmountOfSeats(new CreateUsingCinemaHallCommand("res1", 1));
            createReservationWithAmountOfSeats(new CreateUsingCinemaHallCommand("res2", 2));
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void createReservationWithAmountOfSeats(CreateUsingCinemaHallCommand createCommand) throws Exception {
        reservationService.create(createCommand);
    }
}
