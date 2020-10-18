package com.codehunter.countdowntimer.ca.core.port.out;

import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;

public interface CreateEventPort {
    Event createEvent(CreateEventUseCase.CreateEventIn event);
}
