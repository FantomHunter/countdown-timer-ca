package com.codehunter.countdowntimer.ca.core.port.out;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;

public interface ICreateEventPort {
    Event createEvent(ICreateEventUseCase.CreateEventIn event);
}
