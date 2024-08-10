package pl.lodz.p.cinema_management.order.query.facade;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Named;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.SeatId;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface PageOrderDtoMapper {

    @Mapping(target = "orders", qualifiedByName = "toOrderDtoList")
    PageOrderDto toPageDto(PageOrder domain);

    @Named("toOrderDtoList")
    @IterableMapping(qualifiedByName = "orderToOrderDto")
    List<OrderDto> toListDto(List<Order> orders);

    @Named("orderToOrderDto")
    @Mapping(target = "orderNumber", source = "orderNumber.orderNumber")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "filmShowId", source = "filmShowId.filmShowId")
    @Mapping(target = "price", source = "price.price")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "seatIdsToStrings")
    OrderDto toDto(Order domain);

    @Named("seatIdsToStrings")
    default List<String> seatIdsToStrings(List<SeatId> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::asString).collect(Collectors.toList()) : null;
    }

    @Named("stringsToSeatIds")
    default List<SeatId> stringsToSeatIds(List<String> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::new).collect(Collectors.toList()) : null;
    }

}
