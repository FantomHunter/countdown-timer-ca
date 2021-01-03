package com.codehunter.countdowntimer.ca.core.port.in;

import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.Date;

public interface ICreateEventUseCase {
    Event createEvent(CreateEventIn createEventIn);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateEventIn extends SelfValidating<CreateEventIn> {
        @NonNull String eventName;

        @NonNull Date eventDate;

        public CreateEventIn(@NonNull String eventName, @NonNull Date eventDate) {
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.validateSelf();
        }
    }
}
