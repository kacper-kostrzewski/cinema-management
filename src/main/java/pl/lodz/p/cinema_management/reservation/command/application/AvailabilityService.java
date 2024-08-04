package pl.lodz.p.cinema_management.reservation.command.application;

import java.time.LocalDateTime;

public interface AvailabilityService {
   public void lockTimeFrame(String cinemaHallName, String reservationNumber, LocalDateTime lockStart, Integer duration);
}
