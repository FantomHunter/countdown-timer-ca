package com.codehunter.countdowntimer.ca.core.port.out.user;

import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.Email;

public interface IHasUserPort {
    boolean hasUser(HasUserIn hasUserIn);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class HasUserIn extends SelfValidating<HasUserIn> {
        @NonNull
        String name;

        @NonNull
        @Email
        String email;

        public HasUserIn(@NonNull String name, @NonNull @Email String email) {
            this.name = name;
            this.email = email;
            validateSelf();
        }
    }
}
