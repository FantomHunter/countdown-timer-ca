package com.codehunter.countdowntimer.ca.core;

import java.util.List;

import javax.transaction.Transactional;

import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetAllEventService implements IGetAllEventUseCase, IGetAllEventWithUserUseCase {
    private final IGetAllEventPort getAllEventPort;

    @Override
    public List<Event> getAllEvent() {
        return getAllEventPort.getAllEvents();
    }

    @Override
    public List<Event> getAllEventWithUser(User user) {
        return getAllEventPort.getAllEventsWithUser(user);
    }
}
