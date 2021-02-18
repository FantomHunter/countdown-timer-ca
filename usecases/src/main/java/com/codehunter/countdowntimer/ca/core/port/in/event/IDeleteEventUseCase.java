package com.codehunter.countdowntimer.ca.core.port.in.event;

import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

public interface IDeleteEventUseCase {
    String DELETE_EVENT_SUCCESS = "Delete event success";
    String DELETE_EVENT_NOT_EXIST = "Event not exist";
    String deleteEvent(DeleteEventIn deleteEventIn);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class DeleteEventIn extends SelfValidating<DeleteEventIn> {
        @NonNull Long eventId;

        public DeleteEventIn(@NonNull Long eventId) {
            this.eventId = eventId;
            this.validateSelf();
        }
    }
}
