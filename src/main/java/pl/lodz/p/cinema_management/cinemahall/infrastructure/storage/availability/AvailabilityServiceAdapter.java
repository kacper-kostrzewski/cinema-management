package pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.cinemahall.application.AvailabilityService;


@Component("cinemahallAvailabilityServiceAdapter")
@RequiredArgsConstructor
public class AvailabilityServiceAdapter implements AvailabilityService {

    private final pl.lodz.p.cinema_management.availability.command.application.AvailabilityService availabilityService;

    @Override
    public void createCinemaHallAvailability(String cinemaHallName) {
      availabilityService.create(new pl.lodz.p.cinema_management.availability.command.application.CreateCommand(cinemaHallName));
    }

    @Override
    public void removeCinemaHallAvailability(String cinemaHallName) {
        availabilityService.remove(new pl.lodz.p.cinema_management.availability.command.application.RemoveCommand(cinemaHallName));
    }

}
