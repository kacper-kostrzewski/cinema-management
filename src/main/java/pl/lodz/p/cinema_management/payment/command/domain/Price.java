package pl.lodz.p.cinema_management.payment.command.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Price(BigDecimal price) {

    public static Price of(BigDecimal price) {
        return new Price(price.setScale(2, RoundingMode.HALF_UP));
    }

    public static Price of(Double price) {
        return new Price(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP));
    }

    public static Price of(Integer price) {
        return new Price(BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP));
    }

    public String asString() {
        return price.toString();
    }

    public BigDecimal value() {
        return price;
    }

}
