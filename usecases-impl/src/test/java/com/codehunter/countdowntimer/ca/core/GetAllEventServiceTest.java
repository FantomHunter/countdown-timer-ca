package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

public class GetAllEventServiceTest {
    private final IGetAllEventPort getAllEventPort = Mockito.mock(IGetAllEventPort.class);
    private final IGetAllEventUseCase getAllEventService = new GetAllEventService(getAllEventPort);
    private final IGetAllEventWithUserUseCase getAllEventWithUserService = new GetAllEventService(getAllEventPort);

    @Test
    public void getAllEvent_thenGetAllEventPortCalled() {
        getAllEventService.getAllEvent();
        then(getAllEventPort).should(times(1)).getAllEvents();
    }

    @Test
    public void getAllEvent_whenGetAllEventPortReturned_thenReturnAll() {

        ZonedDateTime eventTime = ZonedDateTime.of(LocalDateTime.of(2020, Month.JUNE, 19, 16, 0, 0), ZoneOffset.UTC);
        List<Event> expected = Collections.singletonList(Event.withId(
                new Event.EventId(1L),
                "name",
                eventTime));
        when(getAllEventPort.getAllEvents())
                .thenReturn(Collections.singletonList(Event.withId(
                        new Event.EventId(1L),
                        "name",
                        eventTime)));
        List<Event> actual = getAllEventService.getAllEvent();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));

    }

    @Test
    public void getAllEventWithUser_whenGetAllEventPortReturned_thenReturnAll() {
        ZonedDateTime eventTime = ZonedDateTime.of(LocalDateTime.of(2020, Month.JUNE, 19, 16, 0, 0), ZoneOffset.UTC);
        List<Event> expected = Collections.singletonList(Event.withId(
                new Event.EventId(1L),
                "name",
                eventTime));
        when(getAllEventPort.getAllEventsWithUser(any(User.class)))
                .thenReturn(Collections.singletonList(Event.withId(
                        new Event.EventId(1L),
                        "name",
                        eventTime)));
        List<Event> actual = getAllEventWithUserService.getAllEventWithUser(User.withId("id", "name"));
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));

    }
}
