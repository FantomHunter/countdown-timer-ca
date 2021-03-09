package com.codehunter.countdowntimer.ca.core.port.in;

import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

public interface IDeleteEventWithUserUseCase {
    String DELETE_EVENT_WITH_USER_SUCCESS = "Delete event success for user";
    String DELETE_EVENT_WITH_USER_NOT_EXIST = "Event not exist for user";

    String deleteEventWithUser(DeleteEventWithUserIn deleteEventWithUserIn);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class DeleteEventWithUserIn extends SelfValidating<DeleteEventWithUserIn> {
        @NonNull Long eventId;
        @NonNull User user;

        public DeleteEventWithUserIn(@NonNull Long eventId, @NonNull User user) {
            this.eventId = eventId;
            this.user = user;
            this.validateSelf();
        }
    }
}
