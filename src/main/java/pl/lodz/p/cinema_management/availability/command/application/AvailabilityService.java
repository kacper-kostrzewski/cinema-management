package pl.lodz.p.cinema_management.availability.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.availability.command.domain.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log
public class AvailabilityService {


    private final CinemaHallAvailabilityRepository cinemaHallAvailabilityRepository;

    public CinemaHallAvailability create(final CreateCommand createCommand) {
        return cinemaHallAvailabilityRepository.save(new CinemaHallAvailability(createCommand.cinemaHallName()));
    }

    public CinemaHallAvailability findByCinemaHallName(String cinemaHallName) {
        return cinemaHallAvailabilityRepository.findByCinemaHallName(cinemaHallName)
                .orElseThrow(CinemaHallNotFoundException::new);
    }

    public void lockCinemaHall(LockCommand lockCommand) {

        Integer lockBuffer = 30;

        LocalDateTime lockStart = lockCommand.lockStart();
        LocalDateTime lockFinish = lockStart.plusMinutes(lockCommand.duration() + lockBuffer);
        TimeSlot timeSlot = new TimeSlot(lockStart, lockFinish);

        CinemaHallAvailability cinemaHallAvailability = cinemaHallAvailabilityRepository.findByCinemaHallName(lockCommand.cinemaHallName())
                .orElseThrow(CinemaHallNotFoundException::new);

        ReservationTimeSlot newWindow = new ReservationTimeSlot(lockCommand.filmShowNumber(), timeSlot);
        cinemaHallAvailability.lockForGivenTimeInterval(newWindow);

        log.info(String.format("%s was successfully locked from %s to %s for film show number '%s'",
                lockCommand.cinemaHallName(),
                lockStart,
                lockFinish,
                lockCommand.filmShowNumber()));
    }


    public void unlockTimeFrame(UnlockCommand unlockCommand) {
        CinemaHallAvailability cinemaHallAvailability = cinemaHallAvailabilityRepository.findByCinemaHallName(unlockCommand.cinemaHallName())
                .orElseThrow(CinemaHallNotFoundException::new);

        cinemaHallAvailability.unlockTimeFrame(unlockCommand.filmShowNumber());

        log.info(String.format("%s was successfully unlocked", unlockCommand.cinemaHallName()));
    }

}
