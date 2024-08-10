package pl.lodz.p.cinema_management.ticket.command.domain;

public record SeatId(String seatId) {

    public static SeatId of(String seatId) {
        return new SeatId(seatId);
    }

    public String asString() {
        return seatId;
    }

}
