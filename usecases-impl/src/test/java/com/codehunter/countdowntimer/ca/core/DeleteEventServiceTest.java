package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IDeleteEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasEventPort;
import com.codehunter.countdowntimer.ca.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteEventServiceTest {
    private final IDeleteEventPort deleteEventPort = Mockito.mock(IDeleteEventPort.class);
    private final IHasEventPort hasEventPort = Mockito.mock(IHasEventPort.class);
    private final IDeleteEventUseCase deleteEventService = new DeleteEventService(deleteEventPort, hasEventPort);
    private final IDeleteEventWithUserUseCase deleteEventwithUserService = new DeleteEventService(deleteEventPort, hasEventPort);

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

    @Test
    void deleteEventWithUser_withIdNotExist_thenReturnIdNotFound() {
        when(hasEventPort.hasEventWithUser(anyLong(), any(User.class))).thenReturn(false);

        String actual = deleteEventwithUserService.deleteEventWithUser(
                new IDeleteEventWithUserUseCase.DeleteEventWithUserIn(
                        8L, User.withId("user-id", null)));

        assertEquals(IDeleteEventWithUserUseCase.DELETE_EVENT_WITH_USER_NOT_EXIST, actual);
    }

    @Test
    void deleteEventWithUser_withValidId_thenReturnSuccessMessage() {
        when(hasEventPort.hasEventWithUser(anyLong(), any(User.class))).thenReturn(true);
        doNothing().when(deleteEventPort).deleteEventWithUser(anyLong(), any(User.class));

        String actual = deleteEventwithUserService.deleteEventWithUser(
                new IDeleteEventWithUserUseCase.DeleteEventWithUserIn(
                        8L, User.withId("user-id", null)));

        assertEquals(IDeleteEventWithUserUseCase.DELETE_EVENT_WITH_USER_SUCCESS, actual);
    }
}
