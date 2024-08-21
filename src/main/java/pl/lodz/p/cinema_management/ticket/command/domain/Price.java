package pl.lodz.p.cinema_management.ticket.command.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Price(BigDecimal amount) {

    public static Price of(BigDecimal amount) {
        return new Price(amount.setScale(2, RoundingMode.HALF_UP));
    }

    public static Price of(Double amount) {
        return new Price(BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP));
    }

    public static Price of(Integer amount) {
        return new Price(BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP));
    }

    public String asString() {
        return amount.toString();
    }

    public BigDecimal value() {
        return amount;
    }

}
