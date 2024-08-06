package pl.lodz.p.cinema_management.availability.command.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.availability.command.domain.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvailabilityService {


    private final CinemaHallAvailabilityRepository cinemaHallAvailabilityRepository;

    public CinemaHallAvailability create(final CreateCommand createCommand) {
        return cinemaHallAvailabilityRepository.save(new CinemaHallAvailability(createCommand.cinemaHallName()));
    }

    public CinemaHallAvailability findByCinemaHallName(String cinemaHallName) {
        return cinemaHallAvailabilityRepository.findByCinemaHallName(cinemaHallName)
                .orElseThrow(CinemaHallNotFoundException::new);
    }

    public void lockTimeFrame(LockCommand lockCommand) {

        Integer lockBuffer = 30;

        LocalDateTime lockStart = lockCommand.lockStart();
        LocalDateTime lockFinish = lockStart.plusMinutes(lockCommand.duration() + lockBuffer);
        TimeFrame timeFrame = new TimeFrame(lockStart, lockFinish);

        CinemaHallAvailability cinemaHallAvailability = cinemaHallAvailabilityRepository.findByCinemaHallName(lockCommand.cinemaHallName())
                .orElseThrow(CinemaHallNotFoundException::new);

        ReservationLock newLock = new ReservationLock(lockCommand.reservationNumber(), timeFrame);
        cinemaHallAvailability.lockTimeFrame(newLock);

        System.out.println("####### Time frame locked successfully");
    }


    public void unlockTimeFrame(UnlockCommand unlockCommand) {
        CinemaHallAvailability cinemaHallAvailability = cinemaHallAvailabilityRepository.findByCinemaHallName(unlockCommand.cinemaHallName())
                .orElseThrow(CinemaHallNotFoundException::new);

        cinemaHallAvailability.unlockTimeFrame(unlockCommand.reservationNumber());

        System.out.println("####### Time frame unlocked successfully");
    }

}
