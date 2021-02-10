package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
        List<Event> expected = Arrays.asList(Event.withId(new Event.EventId(1L), "name", new GregorianCalendar(2021,5, 19).getTime()));
        when(getAllEventPort.getAllEvents()).thenReturn(Arrays.asList(Event.withId(new Event.EventId(1L), "name", new GregorianCalendar(2021,5, 19).getTime())));
        List<Event> actual = getAllEventService.getAllEvent();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));

    }
}
