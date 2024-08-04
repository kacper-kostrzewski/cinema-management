package pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.cinemahall.application.AvailabilityService;


@Component
@RequiredArgsConstructor
public class AvailabilityServiceAdapter implements AvailabilityService {

    private final pl.lodz.p.cinema_management.availability.command.application.AvailabilityService availabilityService;

    @Override
    public void createCinemaHallAvailability(String cinemaHallName) {
      availabilityService.create(new pl.lodz.p.cinema_management.availability.command.application.CreateCommand(cinemaHallName));
    }


}
