package pl.lodz.p.cinema_management.availability.command.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
public class TimeSlot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new StartAfterEndException();
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setTimeSlotStart(LocalDateTime startTime) {
        if (startTime.isAfter(this.endTime)) {
            throw new StartAfterEndException();
        }
        this.startTime = startTime;
    }

    public void setTimeSlotFinish(LocalDateTime endTime) {
        if (endTime.isBefore(this.startTime)) {
            throw new EndBeforeStartException();
        }
        this.endTime = endTime;
    }

    public boolean overlaps(TimeSlot other) {
        return (this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime)) ||
                (other.startTime.isBefore(this.endTime) && other.endTime.isAfter(this.startTime));
    }

}
