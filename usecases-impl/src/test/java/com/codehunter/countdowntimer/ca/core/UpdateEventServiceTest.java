package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IHasEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IUpdateEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

public class UpdateEventServiceTest {
    private final IHasEventPort hasEventPort = Mockito.mock(IHasEventPort.class);
    private final IUpdateEventPort updateEventPort = Mockito.mock(IUpdateEventPort.class);
    private final IUpdateEventUseCase updateEventService = new UpdateEventService(hasEventPort, updateEventPort);

    @Test
    void updateEvent_withInvalidEvent_thenReturnUpdateFailMessage() {
        when(hasEventPort.hasEvent(anyLong())).thenReturn(false);

        Date updateTime = new GregorianCalendar(2021, Calendar.AUGUST, 15).getTime();
        IUpdateEventUseCase.UpdateEventIn input = new IUpdateEventUseCase.UpdateEventIn(5L, "event update", updateTime);
        String actual = updateEventService.updateEvent(input);
        assertEquals(actual, IUpdateEventUseCase.UPDATE_EVENT_NOT_EXIST);
    }

    @Test
    void updateEvent_withValidEvent_thenReturnUpdateSuccessMessage() {
        Date updateTime = new GregorianCalendar(2021, Calendar.AUGUST, 15).getTime();
        when(hasEventPort.hasEvent(anyLong())).thenReturn(true);
        when(updateEventPort.updateEvent(any(IUpdateEventUseCase.UpdateEventIn.class)))
                .thenReturn(Event.withId(new Event.EventId(1L), "event update", updateTime));

        IUpdateEventUseCase.UpdateEventIn input = new IUpdateEventUseCase.UpdateEventIn(1L, "event update", updateTime);
        String actual = updateEventService.updateEvent(input);
        assertEquals(actual, IUpdateEventUseCase.UPDATE_EVENT_SUCCESS);
    }

    @Test
    void updateEvent_withNullDate_thenReturnException() {
        try {
            IUpdateEventUseCase.UpdateEventIn input = new IUpdateEventUseCase.UpdateEventIn(5L, "event update", null);
            updateEventService.updateEvent(input);
        }catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }


}
