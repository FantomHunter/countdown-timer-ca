package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IDeleteEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasEventPort;
import com.codehunter.countdowntimer.ca.persistence.UseCase;
import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class DeleteEventService implements IDeleteEventUseCase, IDeleteEventWithUserUseCase {
    private final IDeleteEventPort deleteEventPort;
    private final IHasEventPort hasEventPort;

    @Override
    public String deleteEvent(DeleteEventIn deleteEventIn) {
        if (hasEventPort.hasEvent(deleteEventIn.getEventId())) {
            deleteEventPort.deleteEvent(deleteEventIn.getEventId());
            return DELETE_EVENT_SUCCESS;
        }
        return DELETE_EVENT_NOT_EXIST;
    }

    @Override
    public String deleteEventWithUser(DeleteEventWithUserIn deleteEventWithUserIn) {
        if (hasEventPort.hasEventWithUser(deleteEventWithUserIn.getEventId(), deleteEventWithUserIn.getUser())) {
            deleteEventPort.deleteEventWithUser(deleteEventWithUserIn.getEventId(), deleteEventWithUserIn.getUser());
            return DELETE_EVENT_WITH_USER_SUCCESS;
        }
        return DELETE_EVENT_WITH_USER_NOT_EXIST;
    }
}
