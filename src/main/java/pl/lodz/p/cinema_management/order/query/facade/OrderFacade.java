package pl.lodz.p.cinema_management.order.query.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.order.command.domain.Order;
import pl.lodz.p.cinema_management.order.command.domain.OrderNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class OrderFacade {

    private final JpaQueryOrderRepository jpaQueryOrderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final PageOrderDtoMapper pageOrderDtoMapper;

    public OrderDto findByOrderNumber(final String orderNumber) {
        final Optional<Order> maybeOrder = jpaQueryOrderRepository.findByOrderNumber(OrderNumber.of(orderNumber));
        return orderDtoMapper.toDto(maybeOrder.orElseThrow(OrderNotFoundException::new));
    }

    public PageOrderDto findAll(final Pageable pageable) {
        Page<Order> pageOfOrdersEntity = jpaQueryOrderRepository.findAll(pageable);
        List<Order> ordersOnCurrentPage = new ArrayList<>(pageOfOrdersEntity.getContent());

        final PageOrder pageOrder = new PageOrder(
                ordersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfOrdersEntity.getTotalPages(),
                pageOfOrdersEntity.getTotalElements()
        );
        return pageOrderDtoMapper.toPageDto(pageOrder);
    }

}
