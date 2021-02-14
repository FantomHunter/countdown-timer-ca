package com.codehunter.countdowntimer.ca.core.port.out;

import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;

public interface IUpdateEventPort {
    Event updateEvent(IUpdateEventUseCase.UpdateEventIn event);
}
