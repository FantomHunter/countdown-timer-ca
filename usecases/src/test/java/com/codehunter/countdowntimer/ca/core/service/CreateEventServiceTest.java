package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import static org.mockito.BDDMockito.*;

public class CreateEventServiceTest {
    private final ICreateEventPort createEventPort = Mockito.mock(ICreateEventPort.class);
    private final CreateEventService createEventService = new CreateEventService(createEventPort);

    @Test
    void createEvent_withValidInput_thenDataIsPassedToPort() {
        ICreateEventUseCase.CreateEventIn createEventIn = new ICreateEventUseCase.CreateEventIn(
               "event name",
               new Date()
        );

        createEventService.createEvent(createEventIn);
        then(createEventPort).should(times(1)).createEvent(createEventIn);
    }
}
