package pl.lodz.p.cinema_management.reservation.command.domain;

import java.time.LocalDateTime;

public class ReservationFactory {

    public static Reservation createReservation(String reservationNumber, CinemaHall cinemaHall, Film film, LocalDateTime reservationDateTime) {
        return new Reservation(reservationNumber, cinemaHall.name(), cinemaHall.seatsIdentifiers(), film.name(), reservationDateTime);
    }

    public static Reservation prepareReservationForUser(Reservation reservation, User user) {
        if (user.role() == UserRole.ADMIN) {
            reservation.setBookingPolicy(new AdminBookingPolicy());
            reservation.setReleasingPolicy(new AdminReleasingPolicy());
            return reservation;
        }
        if (user.role() == UserRole.VIP) {
            reservation.setBookingPolicy(new VipBookingPolicy());
            reservation.setReleasingPolicy(new UserReleasingPolicy());
            return reservation;
        }

        reservation.setBookingPolicy(new UserBookingPolicy());
        reservation.setReleasingPolicy(new UserReleasingPolicy());
        return reservation;

    }
}
