package pl.lodz.p.cinema_management.ticket.command.domain;

public record FilmName(String filmName) {

    public static FilmName of(String filmName) {
        return new FilmName(filmName);
    }

    public String asString() {
        return filmName;
    }

}
