package com.codehunter.countdowntimer.ca.core.service;

import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IDeleteEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasEventPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteEventServiceTest {
    private final IDeleteEventPort deleteEventPort = Mockito.mock(IDeleteEventPort.class);
    private final IHasEventPort hasEventPort = Mockito.mock(IHasEventPort.class);
    private final IDeleteEventUseCase deleteEventService = new DeleteEventService(deleteEventPort, hasEventPort);

    @Test
    void deleteEvent_withIdNotExist_thenReturnIdNotFound() {
        when(hasEventPort.hasEvent(anyLong())).thenReturn(false);

        String actual = deleteEventService.deleteEvent(new IDeleteEventUseCase.DeleteEventIn(8L));

        assertEquals("Event not exist", actual);
    }

    @Test
    void deleteEvent_withValidId_thenReturnSuccessMessage() {
        when(hasEventPort.hasEvent(anyLong())).thenReturn(true);
        doNothing().when(deleteEventPort).deleteEvent(anyLong());

        String actual = deleteEventService.deleteEvent(new IDeleteEventUseCase.DeleteEventIn(8L));

        assertEquals("Delete event success", actual);
    }

}
