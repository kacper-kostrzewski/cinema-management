package pl.lodz.p.cinema_management.domain.cinemahall;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.smalaca.annotation.ddd.AggregateRoot;

@AggregateRoot
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CinemaHall {
    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private Integer capacity;
}
