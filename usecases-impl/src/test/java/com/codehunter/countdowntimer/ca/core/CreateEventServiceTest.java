package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateUserPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

public class CreateEventServiceTest {
    private final ICreateEventPort createEventPort = Mockito.mock(ICreateEventPort.class);
    private final ICreateUserPort createUserPort = Mockito.mock(ICreateUserPort.class);
    private final IHasUserPort hasUserPort = Mockito.mock(IHasUserPort.class);
    private final CreateEventService instanceTest = new CreateEventService(createEventPort, createUserPort, hasUserPort);

    @Test
    void createEvent_withValidInput_thenDataIsPassedToPort() {
        ICreateEventUseCase.CreateEventIn createEventIn = new ICreateEventUseCase.CreateEventIn(
                "event name",
                new Date()
        );

        instanceTest.createEvent(createEventIn);
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

        Event result = instanceTest.createEvent(createEventIn);
        assertNotNull(result);
    }

    @Test
    void createEventWithUser_withExistedUser_thenNewEventReturned() {
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        ICreateEventWithUserUseCase.CreateEventWithUserIn createEventWithUserIn = new ICreateEventWithUserUseCase.CreateEventWithUserIn(
                User.withId("user-id", "username"),
                "event name",
                eventTime
        );

        when(hasUserPort.hasUser(User.withId("user-id", "username")))
                .thenReturn(true);
        Event expected = Event.withId(new Event.EventId(1L), "event name", eventTime);
        when(createEventPort.createEvent(createEventWithUserIn))
                .thenReturn(expected);

        Event actual = instanceTest.createEvent(createEventWithUserIn);

        assertEquals(expected, actual);
    }

    @Test
    void createEventWithUser_withNonExistedUser_thenNewEventReturned() {
        User user = User.withId("user-id", "username");
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        ICreateEventWithUserUseCase.CreateEventWithUserIn createEventWithUserIn = new ICreateEventWithUserUseCase.CreateEventWithUserIn(
                user,
                "event name",
                eventTime
        );

        when(hasUserPort.hasUser(user)).thenReturn(false);
        when(createUserPort.createUser(user)).thenReturn(user);
        Event expected = Event.withId(new Event.EventId(1L), "event name", eventTime);
        when(createEventPort.createEvent(createEventWithUserIn))
                .thenReturn(expected);

        Event actual = instanceTest.createEvent(createEventWithUserIn);

        assertEquals(expected, actual);
    }
}
