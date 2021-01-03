package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.persistence.UseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateEventService implements ICreateEventUseCase {
    private final ICreateEventPort createEventPort;

    @Override
    public Event createEvent(CreateEventIn createEventIn) {
        return createEventPort.createEvent(createEventIn);
    }
}
