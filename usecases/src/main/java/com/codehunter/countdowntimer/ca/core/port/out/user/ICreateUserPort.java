package com.codehunter.countdowntimer.ca.core.port.out.user;

import com.codehunter.countdowntimer.ca.core.port.in.user.ICreateUserUseCase;
import com.codehunter.countdowntimer.ca.domain.security.User;

public interface ICreateUserPort {
    User createUser(ICreateUserUseCase.CreateUserIn createUserIn);
}
