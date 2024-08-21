package pl.lodz.p.cinema_management.order.query.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.order.command.domain.*;
import pl.lodz.p.cinema_management.order.command.application.AuthenticationService;

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
    private final AuthenticationService authenticationService;

    public OrderDto findByOrderNumber(final String orderNumber) {
        final Optional<Order> maybeOrder = jpaQueryOrderRepository.findByOrderNumber(OrderNumber.of(orderNumber));
        Order order = maybeOrder.orElseThrow(OrderNotFoundException::new);

        User user = authenticationService.getLoggedInUser();

        if (user == null) {
            throw new RuntimeException("User is not authenticated");
        }

        if (user.role() != UserRole.ADMIN) {
            if (!order.getUserId().equals(UserId.of(user.id()))) {
                throw new RuntimeException("User is not authorized to view this order");
            }
        }

        return orderDtoMapper.toDto(order);
    }

    public PageOrderDto findUserOrders(Pageable pageable) {
        User user = authenticationService.getLoggedInUser();

        if (user == null) {
            throw new RuntimeException("User is not authenticated");
        }

        Page<Order> pageOfOrdersEntity = jpaQueryOrderRepository.findAllByUserId(UserId.of(user.id()), pageable);
        List<Order> ordersOnCurrentPage = new ArrayList<>(pageOfOrdersEntity.getContent());

        final PageOrder pageOrder = new PageOrder(
                ordersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfOrdersEntity.getTotalPages(),
                pageOfOrdersEntity.getTotalElements()
        );
        return pageOrderDtoMapper.toPageDto(pageOrder);
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
