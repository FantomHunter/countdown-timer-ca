package com.codehunter.countdowntimer.ca.core.port.in;

import com.codehunter.countdowntimer.ca.core.service.UpdateEventService;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.Date;

public interface IUpdateEventUseCase {
    String UPDATE_EVENT_SUCCESS = "Update event success";
    String UPDATE_EVENT_NOT_EXIST = "Event not exist";

    String updateEvent(UpdateEventIn event);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateEventIn extends SelfValidating<UpdateEventIn> {
        @NonNull Long id;
        @NonNull String name;
        @NonNull Date time;

        public UpdateEventIn(@NonNull Long id, @NonNull String name, @NonNull Date time) {
            this.id = id;
            this.name = name;
            this.time = time;
            this.validateSelf();
        }
    }
}
