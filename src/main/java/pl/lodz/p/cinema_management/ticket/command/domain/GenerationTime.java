package pl.lodz.p.cinema_management.ticket.command.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record GenerationTime(LocalDateTime generatedAt) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static GenerationTime of(LocalDateTime generatedAt) {
        return new GenerationTime(generatedAt);
    }

    public String asString() {
        return generatedAt.format(FORMATTER);
    }

    public LocalDateTime asLocalDateTime() {
        return generatedAt;
    }

}