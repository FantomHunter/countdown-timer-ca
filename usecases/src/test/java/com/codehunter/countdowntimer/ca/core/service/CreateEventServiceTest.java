package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void createEvent_withValidInput_thenNewEventReturn() {
        when(createEventPort.createEvent(any(ICreateEventUseCase.CreateEventIn.class)))
                .thenReturn(Event.withId(new Event.EventId(1L), "name", new Date()));

        ICreateEventUseCase.CreateEventIn createEventIn = new ICreateEventUseCase.CreateEventIn(
                "event name",
                new Date()
        );

        Event result = createEventService.createEvent(createEventIn);
        Assertions.assertNotNull(result);
    }
}
