package com.codehunter.countdowntimer.ca.core.port.out;

import java.util.List;

import com.codehunter.countdowntimer.ca.domain.Event;

public interface IGetAllEventPort {
    List<Event> getAllEvents();
    
}
