package pl.lodz.p.cinema_management.order.query.facade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.SeatId;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    @Mapping(target = "orderNumber", source = "orderNumber.orderNumber")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "filmShowId", source = "filmShowId.filmShowId")
    @Mapping(target = "price", source = "price.price")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "seatIdsToStrings")
    OrderDto toDto(Order domain);

    @Mapping(target = "orderNumber.orderNumber", source = "orderNumber")
    @Mapping(target = "userId.userId", source = "userId")
    @Mapping(target = "filmShowId.filmShowId", source = "filmShowId")
    @Mapping(target = "price.price", source = "price")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "stringsToSeatIds")
    Order toDomain(OrderDto dto);

    @Named("seatIdsToStrings")
    default List<String> seatIdsToStrings(List<SeatId> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::asString).collect(Collectors.toList()) : null;
    }

    @Named("stringsToSeatIds")
    default List<SeatId> stringsToSeatIds(List<String> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::new).collect(Collectors.toList()) : null;
    }

}
