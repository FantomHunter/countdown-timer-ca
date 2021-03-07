package com.codehunter.countdowntimer.ca.core;

import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IHasEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IUpdateEventPort;
import com.codehunter.countdowntimer.ca.persistence.UseCase;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@AllArgsConstructor
@Transactional
public class UpdateEventService implements IUpdateEventUseCase {
    private final IHasEventPort hasEventPort;
    private final IUpdateEventPort updateEventPort;

    @Override
    public String updateEvent(UpdateEventIn event) {
        if (!hasEventPort.hasEvent(event.getId())) {
            return IUpdateEventUseCase.UPDATE_EVENT_NOT_EXIST;
        }
        updateEventPort.updateEvent(event);
        return IUpdateEventUseCase.UPDATE_EVENT_SUCCESS;
    }
}
