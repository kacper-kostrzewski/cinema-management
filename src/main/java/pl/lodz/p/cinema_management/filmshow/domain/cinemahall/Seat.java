package pl.lodz.p.cinema_management.filmshow.domain.cinemahall;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private Integer rowNumber;
    private Integer seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
}
