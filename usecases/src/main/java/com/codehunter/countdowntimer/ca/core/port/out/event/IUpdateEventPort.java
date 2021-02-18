package com.codehunter.countdowntimer.ca.core.port.out.event;

import com.codehunter.countdowntimer.ca.core.port.in.event.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;

public interface IUpdateEventPort {
    Event updateEvent(IUpdateEventUseCase.UpdateEventIn event);
}
