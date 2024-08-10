package pl.lodz.p.cinema_management.order.command.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeatId {

    private String seatId;

    public static SeatId of(String seatId) {
        return new SeatId(seatId);
    }

    public static List<SeatId> of(List<String> seatIds) {
        return seatIds.stream()
                .map(SeatId::of)
                .collect(Collectors.toList());
    }

    public String asString() {
        return seatId;
    }

}
