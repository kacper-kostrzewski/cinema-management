package pl.lodz.p.cinema_management.ticket.command.domain;

public record FilmShowId(Integer filmShowId) {

    public static FilmShowId of(Integer filmShowId) {
        return new FilmShowId(filmShowId);
    }

    public String asString() {
        return filmShowId.toString();
    }

    public Integer value() {
        return filmShowId;
    }

}
