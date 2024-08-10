package pl.lodz.p.cinema_management.filmshow.command.infrastructure.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.filmshow.command.application.AvailabilityService;

import java.time.LocalDateTime;

@Component("FilmShowAvailabilityServiceAdapter")
@RequiredArgsConstructor
public class AvailabilityServiceAdapter implements AvailabilityService {

    private final pl.lodz.p.cinema_management.availability.command.application.AvailabilityService availabilityService;

    @Override
    public void lockCinemaHall(String cinemaHallName, String filmShowNumber, LocalDateTime lockStart, Integer duration) {
      availabilityService.lockCinemaHall(new pl.lodz.p.cinema_management.availability.command.application.LockCommand(
              cinemaHallName,
              filmShowNumber,
              lockStart,
              duration)
      );

    }


}
