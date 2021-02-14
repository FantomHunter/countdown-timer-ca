package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

public class GetAllEventServiceTest {
    private final IGetAllEventPort getAllEventPort = Mockito.mock(IGetAllEventPort.class);
    private final IGetAllEventUseCase getAllEventService = new GetAllEventService(getAllEventPort);

    @Test
    public void getAllEvent_thenGetAllEventPortCalled(){
        getAllEventService.getAllEvent();
        then(getAllEventPort).should(times(1)).getAllEvents();
    }
    
    @Test
    public void getAllEvent_whenGetAllEventPortReturned_thenReturnAll(){
        List<Event> expected = Collections.singletonList(Event.withId(
                new Event.EventId(1L),
                "name",
                new GregorianCalendar(2021, Calendar.JUNE, 19).getTime()));
        when(getAllEventPort.getAllEvents())
                .thenReturn(Collections.singletonList(Event.withId(
                        new Event.EventId(1L),
                       "name",
                        new GregorianCalendar(2021, Calendar.JUNE, 19).getTime())));
        List<Event> actual = getAllEventService.getAllEvent();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));

    }
}
