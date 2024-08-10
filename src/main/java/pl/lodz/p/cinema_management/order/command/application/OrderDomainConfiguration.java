package pl.lodz.p.cinema_management.order.command.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.order.command.domain.OrderRepository;
import pl.lodz.p.cinema_management.order.command.infrastructure.storage.JpaOrderRepository;
import pl.lodz.p.cinema_management.order.command.infrastructure.storage.OrderStorageAdapter;


@Configuration
@ConfigurationProperties("order.domain.properties")
public class OrderDomainConfiguration {

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpaOrderRepository) {
        return new OrderStorageAdapter(jpaOrderRepository);
    }

}
