package pl.lodz.p.cinema_management.filmshow.command.domain;

public record OrderNumber(String orderNumber) {

    public static OrderNumber of(String orderNumber) {
        return new OrderNumber(orderNumber);
    }

    public String asString() {
        return orderNumber;
    }

}
