package pl.lodz.p.cinema_management.availability.command.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
public class TimeFrame {

    private LocalDateTime timeFrameStart;
    private LocalDateTime timeFrameFinish;

    public TimeFrame(LocalDateTime timeFrameStart, LocalDateTime timeFrameFinish) {
        if (timeFrameStart.isAfter(timeFrameFinish)) {
            throw new StartAfterEndException();
        }
        this.timeFrameStart = timeFrameStart;
        this.timeFrameFinish = timeFrameFinish;
    }

    public void setTimeFrameStart(LocalDateTime timeFrameStart) {
        if (timeFrameStart.isAfter(this.timeFrameFinish)) {
            throw new StartAfterEndException();
        }
        this.timeFrameStart = timeFrameStart;
    }

    public void setTimeFrameFinish(LocalDateTime timeFrameFinish) {
        if (timeFrameFinish.isBefore(this.timeFrameStart)) {
            throw new EndBeforeStartException();
        }
        this.timeFrameFinish = timeFrameFinish;
    }

    public boolean overlaps(TimeFrame other) {
        return (this.timeFrameStart.isBefore(other.timeFrameFinish) && this.timeFrameFinish.isAfter(other.timeFrameStart)) ||
                (other.timeFrameStart.isBefore(this.timeFrameFinish) && other.timeFrameFinish.isAfter(this.timeFrameStart));
    }

}
