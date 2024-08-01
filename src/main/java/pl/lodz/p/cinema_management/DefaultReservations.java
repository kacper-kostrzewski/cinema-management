package pl.lodz.p.cinema_management;

import pl.lodz.p.cinema_management.reservation.command.application.CreateCommand;
import pl.lodz.p.cinema_management.reservation.command.application.ReservationService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log
public class DefaultReservations implements CommandLineRunner {

    private final ReservationService reservationService;

    public DefaultReservations(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) {
        try {
            createReservationWithAmountOfSeats(new CreateCommand("res1", 10));
            createReservationWithAmountOfSeats(new CreateCommand("res2", 5));
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void createReservationWithAmountOfSeats(CreateCommand createCommand) throws Exception {
        reservationService.create(createCommand);
    }
}
