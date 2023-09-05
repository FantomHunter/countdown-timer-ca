package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateUserPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.UseCase;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateEventService implements ICreateEventUseCase, ICreateEventWithUserUseCase {
    private final ICreateEventPort createEventPort;
    private final ICreateUserPort createUserPort;
    private final IHasUserPort hasUserPort;

    @Override
    public Event createEvent(CreateEventIn createEventIn) {
        return createEventPort.createEvent(createEventIn);
    }

    @Override
    public Event createEvent(CreateEventWithUserIn createEventWithUserIn) {
        if (hasUserPort.hasUser(createEventWithUserIn.getUser())) {
            return createEventPort.createEvent(createEventWithUserIn);
        }
        if (createUserPort.createUser(createEventWithUserIn.getUser()) != null) {
            return createEventPort.createEvent(createEventWithUserIn);
        }
        return null;
    }
}
