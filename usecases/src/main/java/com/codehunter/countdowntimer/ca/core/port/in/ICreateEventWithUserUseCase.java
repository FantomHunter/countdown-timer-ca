package com.codehunter.countdowntimer.ca.core.port.in;

import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

public interface ICreateEventWithUserUseCase {

    Event createEvent(CreateEventWithUserIn createEventWithUserIn);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateEventWithUserIn extends SelfValidating<ICreateEventWithUserUseCase.CreateEventWithUserIn> {
        @NonNull User user;
        @NonNull String eventName;
        @NonNull ZonedDateTime eventDate;

        public CreateEventWithUserIn(@NonNull User user, @NonNull String eventName, @NonNull ZonedDateTime eventDate) {
            this.user = user;
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.validateSelf();
        }
    }
}
