package pl.lodz.p.cinema_management.filmshow.command.application;

import java.time.LocalDateTime;

public interface AvailabilityService {
   void lockCinemaHall(String cinemaHallName, String filmShowNumber, LocalDateTime lockStart, Integer duration);
}
