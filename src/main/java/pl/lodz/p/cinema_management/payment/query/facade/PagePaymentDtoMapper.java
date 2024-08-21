package pl.lodz.p.cinema_management.payment.query.facade;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.SeatId;


import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PagePaymentDtoMapper {

    @Mapping(target = "payments", qualifiedByName = "toPaymentDtoList")
    PagePaymentDto toPageDto(PagePayment domain);

    @Named("toPaymentDtoList")
    @IterableMapping(qualifiedByName = "paymentToPaymentDto")
    List<PaymentDto> toListDto(List<Payment> payments);

    @Named("paymentToPaymentDto")
    @Mapping(target = "paymentNumber", source = "paymentNumber.paymentNumber")
    @Mapping(target = "orderId", source = "orderId.orderId")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "filmShowId", source = "filmShowId.filmShowId")
    @Mapping(target = "price", source = "price.price")
    @Mapping(target = "seats", source = "seats", qualifiedByName = "seatIdsToStrings")
    PaymentDto toDto(Payment domain);

    @Named("seatIdsToStrings")
    default List<String> seatIdsToStrings(List<SeatId> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::asString).collect(Collectors.toList()) : null;
    }

    @Named("stringsToSeatIds")
    default List<SeatId> stringsToSeatIds(List<String> seatIds) {
        return seatIds != null ? seatIds.stream().map(SeatId::new).collect(Collectors.toList()) : null;
    }

}
