package com.codehunter.countdowntimer.ca.core.port.out;

import com.codehunter.countdowntimer.ca.domain.User;

public interface IDeleteEventPort {
    void deleteEvent(Long eventId);
    void deleteEventWithUser(Long eventId, User user);
}
