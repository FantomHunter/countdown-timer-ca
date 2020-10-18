package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.persistence.UseCase;
import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.CreateEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateEventService implements CreateEventUseCase {
    private final CreateEventPort createEventPort;

    @Override
    public void createEvent(CreateEventIn createEventIn) {
        Event event = createEventPort.createEvent(createEventIn);
    }
}
