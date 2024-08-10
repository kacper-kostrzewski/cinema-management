package pl.lodz.p.cinema_management.payment.command.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.payment.command.domain.PaymentRepository;
import pl.lodz.p.cinema_management.payment.command.infrastructure.storage.JpaPaymentRepository;
import pl.lodz.p.cinema_management.payment.command.infrastructure.storage.PaymentStorageAdapter;

@Configuration
@ConfigurationProperties("payment.domain.properties")
public class PaymentDomainConfiguration {

    @Bean
    public PaymentRepository paymentRepository(JpaPaymentRepository jpaPaymentRepository) {
        return new PaymentStorageAdapter(jpaPaymentRepository);
    }

}
