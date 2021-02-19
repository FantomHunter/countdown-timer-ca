package com.codehunter.countdowntimer.ca.core.service.user;

import com.codehunter.countdowntimer.ca.core.port.in.user.ICreateUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.user.ICreateUserPort;
import com.codehunter.countdowntimer.ca.core.port.out.user.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.security.ERole;
import com.codehunter.countdowntimer.ca.domain.security.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserServiceTest {
    private final ICreateUserPort createUserPort = Mockito.mock(ICreateUserPort.class);
    private final IHasUserPort hasUserPort = Mockito.mock(IHasUserPort.class);
    private final CreateUserService createUserService = new CreateUserService(createUserPort,hasUserPort);

    @Test
    void createUser_withExistUser_thenReturnEmptyUser() {
        when(hasUserPort.hasUser(any())).thenReturn(true);
        Optional<User> user = createUserService.createUser(new ICreateUserUseCase.CreateUserIn("username", "email@gmail.com","password"));
        assertEquals(false, user.isPresent());
    }

    @Test
    void createUser_withNotExistUser_theReturnNewUser() {
        when(hasUserPort.hasUser(any())).thenReturn(false);
        Set<ERole> roles = Collections.singleton(ERole.USER);
        User expected = new User(new User.UserId(1L), "username", "password", "email@gmail.com", roles);
        when(createUserPort.createUser(any())).thenReturn(expected);

        Optional<User> user = createUserService.createUser(new ICreateUserUseCase.CreateUserIn("username", "email@gmail.com","password"));
        assertEquals(true, user.isPresent());
        assertEquals(expected, user.get());
    }
}
