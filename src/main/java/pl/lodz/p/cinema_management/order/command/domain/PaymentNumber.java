package pl.lodz.p.cinema_management.order.command.domain;

public record PaymentNumber(String paymentNumber) {

    public static PaymentNumber of(String paymentNumber) {
        return new PaymentNumber(paymentNumber);
    }

    public String asString() {
        return paymentNumber;
    }

}
