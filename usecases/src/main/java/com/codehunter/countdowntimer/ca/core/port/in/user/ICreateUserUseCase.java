package com.codehunter.countdowntimer.ca.core.port.in.user;

import com.codehunter.countdowntimer.ca.domain.security.User;
import com.codehunter.countdowntimer.ca.persistence.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Optional;

public interface ICreateUserUseCase {
    Optional<User> createUser(CreateUserIn createUserIn);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateUserIn extends SelfValidating<CreateUserIn> {
        @NotEmpty
        @Size(max = 20)
        String username;

        @NotEmpty
        @Size(max = 50)
        @Email
        String email;

        @NotEmpty
        @Size(max = 120)
        String password;

        public CreateUserIn(@NotEmpty @Size(max = 20) String username, @NotEmpty @Size(max = 50) @Email String email, @NotEmpty @Size(max = 120) String password) {
            this.username = username;
            this.email = email;
            this.password = password;
            validateSelf();
        }
    }
}
