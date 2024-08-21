package pl.lodz.p.cinema_management.ticket.command.domain;

public record CinemaHallName(String cinemaHallName) {

    public static CinemaHallName of(String cinemaHallName) {
        return new CinemaHallName(cinemaHallName);
    }

    public String asString() {
        return cinemaHallName;
    }

}
