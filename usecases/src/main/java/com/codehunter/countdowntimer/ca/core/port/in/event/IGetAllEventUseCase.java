package com.codehunter.countdowntimer.ca.core.port.in.event;

import java.util.List;

import com.codehunter.countdowntimer.ca.domain.Event;

public interface IGetAllEventUseCase {
    List<Event> getAllEvent();
}
