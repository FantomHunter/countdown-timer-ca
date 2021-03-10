package com.codehunter.countdowntimer.ca.core.port.in;

import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;

import java.util.List;

public interface IGetAllEventWithUserUseCase {
    List<Event> getAllEventWithUser(User user);
}
