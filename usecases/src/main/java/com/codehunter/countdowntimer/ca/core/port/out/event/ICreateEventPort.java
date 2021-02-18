package com.codehunter.countdowntimer.ca.core.port.out.event;

import com.codehunter.countdowntimer.ca.core.port.in.event.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;

public interface ICreateEventPort {
    Event createEvent(ICreateEventUseCase.CreateEventIn event);
}
