package pl.lodz.p.cinema_management.payment.facade;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.SeatId;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

    @Mapping(target = "paymentNumber", source = "paymentNumber.paymentNumber")
    @Mapping(target = "orderId", source = "orderId.orderId")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "filmShowId", source = "filmShowId.filmShowId")
    @Mapping(target = "price", source = "price.price")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "seatIdsToStrings")
    PaymentDto toDto(Payment payment);

    @Mapping(target = "paymentNumber.paymentNumber", source = "paymentNumber")
    @Mapping(target = "orderId.orderId", source = "orderId")
    @Mapping(target = "userId.userId", source = "userId")
    @Mapping(target = "filmShowId.filmShowId", source = "filmShowId")
    @Mapping(target = "price.price", source = "price")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "stringsToSeatIds")
    Payment toDomain(PaymentDto paymentDto);

    @Named("seatIdsToStrings")
    default List<String> seatIdsToStrings(List<SeatId> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::asString).collect(Collectors.toList()) : null;
    }

    @Named("stringsToSeatIds")
    default List<SeatId> stringsToSeatIds(List<String> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::new).collect(Collectors.toList()) : null;
    }

}
