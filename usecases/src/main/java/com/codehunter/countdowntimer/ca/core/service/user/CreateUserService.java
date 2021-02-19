package com.codehunter.countdowntimer.ca.core.service.user;

import com.codehunter.countdowntimer.ca.core.port.in.user.ICreateUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.user.ICreateUserPort;
import com.codehunter.countdowntimer.ca.core.port.out.user.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.security.User;
import com.codehunter.countdowntimer.ca.persistence.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateUserService implements ICreateUserUseCase {
    private final ICreateUserPort createUserPort;
    private final IHasUserPort hasUserPort;

    @Override
    public Optional<User> createUser(CreateUserIn createUserIn) {
        if (!hasUserPort.hasUser(new IHasUserPort.HasUserIn(createUserIn.getUsername(), createUserIn.getEmail()))) {
            return Optional.of(createUserPort.createUser(createUserIn));
        }
        return Optional.empty();
    }
}
