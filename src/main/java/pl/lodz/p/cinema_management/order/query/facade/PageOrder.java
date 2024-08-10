package pl.lodz.p.cinema_management.order.query.facade;

import lombok.Value;
import pl.lodz.p.cinema_management.order.command.domain.Order;

import java.io.Serializable;
import java.util.List;

@Value
public class PageOrder implements Serializable {

    List<Order> orders;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;

}
