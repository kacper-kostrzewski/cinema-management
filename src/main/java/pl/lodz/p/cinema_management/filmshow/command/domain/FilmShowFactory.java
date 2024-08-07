package pl.lodz.p.cinema_management.filmshow.command.domain;

import java.time.LocalDateTime;

public class FilmShowFactory {

    public static FilmShow createFilmShow(String reservationNumber, CinemaHall cinemaHall, Film film, LocalDateTime reservationDateTime) {
        return new FilmShow(reservationNumber, cinemaHall.name(), cinemaHall.seatsIdentifiers(), film.name(), reservationDateTime);
    }

    public static FilmShow prepareReservationForUser(FilmShow filmShow, User user) {
        if (user.role() == UserRole.ADMIN) {
            filmShow.setBookingPolicy(new AdminBookingPolicy());
            filmShow.setReleasingPolicy(new AdminReleasingPolicy());
            return filmShow;
        }
        if (user.role() == UserRole.VIP) {
            filmShow.setBookingPolicy(new VipBookingPolicy());
            filmShow.setReleasingPolicy(new UserReleasingPolicy());
            return filmShow;
        }

        filmShow.setBookingPolicy(new UserBookingPolicy());
        filmShow.setReleasingPolicy(new UserReleasingPolicy());
        return filmShow;

    }
}
