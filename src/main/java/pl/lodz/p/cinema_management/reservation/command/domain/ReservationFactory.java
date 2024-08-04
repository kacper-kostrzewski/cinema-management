package pl.lodz.p.cinema_management.reservation.command.domain;

public class ReservationFactory {

    public static Reservation createReservation(String reservationNumber, CinemaHall cinemaHall) {
        Reservation reservation = new Reservation(reservationNumber, cinemaHall.name(), cinemaHall.seatsIdentifiers());
        return reservation;
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
