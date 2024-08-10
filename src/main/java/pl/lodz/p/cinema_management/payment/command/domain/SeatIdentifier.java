package pl.lodz.p.cinema_management.payment.command.domain;

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
public class SeatIdentifier {

    private String seatIdentifier;

    public static SeatIdentifier of(String seatIdentifier) {
        return new SeatIdentifier(seatIdentifier);
    }

    public static List<SeatIdentifier> of(List<String> seatIdentifiers) {
        return seatIdentifiers.stream()
                .map(SeatIdentifier::of)
                .collect(Collectors.toList());
    }

    public String asString() {
        return seatIdentifier;
    }

}
