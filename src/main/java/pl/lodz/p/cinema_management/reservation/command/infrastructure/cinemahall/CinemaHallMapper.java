package pl.lodz.p.cinema_management.reservation.command.infrastructure.cinemahall;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.cinema_management.reservation.command.domain.CinemaHall;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CinemaHallMapper {

    @Mapping(target = "seatsIdentifiers", source = "seats", qualifiedByName = "mapSeatsToIdentifiers")
    CinemaHall toReservationContext(pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall cinemaHall);

    @Named("mapSeatsToIdentifiers")
    static List<String> mapSeatsToIdentifiers(List<pl.lodz.p.cinema_management.cinemahall.domain.Seat> seats) {
        return seats.stream()
                .map(pl.lodz.p.cinema_management.cinemahall.domain.Seat::getSeatIdentifier)
                .collect(Collectors.toList());
    }

}
