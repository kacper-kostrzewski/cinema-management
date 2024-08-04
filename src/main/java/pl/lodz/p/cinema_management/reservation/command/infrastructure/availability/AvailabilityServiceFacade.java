package pl.lodz.p.cinema_management.reservation.command.infrastructure.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.reservation.command.application.AvailabilityService;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class AvailabilityServiceFacade implements AvailabilityService {

    private final pl.lodz.p.cinema_management.availability.command.application.AvailabilityService availabilityService;

    @Override
    public void lockTimeFrame (String cinemaHallName, String reservationNumber, LocalDateTime lockStart, Integer duration) {
      availabilityService.lockTimeFrame(new pl.lodz.p.cinema_management.availability.command.application.LockCommand(
              cinemaHallName,
              reservationNumber,
              lockStart,
              duration)
      );

    }


}
