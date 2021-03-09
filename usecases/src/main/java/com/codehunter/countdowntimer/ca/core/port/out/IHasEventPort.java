package com.codehunter.countdowntimer.ca.core.port.out;

import com.codehunter.countdowntimer.ca.domain.User;

public interface IHasEventPort {
    boolean hasEvent(Long eventId);
    boolean hasEventWithUser(Long eventId, User user);
}
