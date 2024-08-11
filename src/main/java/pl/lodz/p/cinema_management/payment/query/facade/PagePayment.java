package pl.lodz.p.cinema_management.payment.query.facade;

import lombok.Value;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;

import java.io.Serializable;
import java.util.List;

@Value
public class PagePayment implements Serializable {

    List<Payment> payments;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;

}
