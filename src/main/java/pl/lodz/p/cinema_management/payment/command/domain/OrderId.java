package pl.lodz.p.cinema_management.payment.command.domain;

public record OrderId(Integer orderId) {

    public static OrderId of(Integer orderId) {
        return new OrderId(orderId);
    }

    public String asString() {
        return orderId.toString();
    }

    public Integer value() {
        return orderId;
    }

}
