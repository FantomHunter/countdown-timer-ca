package com.codehunter.countdowntimer.ca.core.service.event;

import com.codehunter.countdowntimer.ca.persistence.UseCase;
import com.codehunter.countdowntimer.ca.core.port.in.event.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.event.ICreateEventPort;
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
