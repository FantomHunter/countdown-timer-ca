package com.codehunter.countdowntimer.ca.core.port.in;

import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.Date;

public interface IUpdateEventWithUserUseCase {

    String UPDATE_EVENT_WITH_USER_SUCCESS = "Update event success";
    String UPDATE_EVENT_WITH_USER_NOT_EXIST = "Event not exist";

    String updateEventWithUser(IUpdateEventWithUserUseCase.UpdateEventWithUserIn event);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class UpdateEventWithUserIn extends SelfValidating<IUpdateEventWithUserUseCase.UpdateEventWithUserIn> {
        @NonNull Long id;
        @NonNull String name;
        @NonNull Date time;
        @NonNull User user;

        public UpdateEventWithUserIn(@NonNull Long id, @NonNull String name, @NonNull Date time, @NonNull User user) {
            this.id = id;
            this.name = name;
            this.time = time;
            this.user = user;
            this.validateSelf();
        }
    }
}
