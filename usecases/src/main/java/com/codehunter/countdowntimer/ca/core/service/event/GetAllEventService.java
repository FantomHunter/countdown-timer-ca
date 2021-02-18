package com.codehunter.countdowntimer.ca.core.service.event;

import java.util.List;

import javax.transaction.Transactional;

import com.codehunter.countdowntimer.ca.core.port.in.event.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.event.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.UseCase;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetAllEventService implements IGetAllEventUseCase {
    private final IGetAllEventPort getAllEventPort;

    @Override
    public List<Event> getAllEvent() {
        return getAllEventPort.getAllEvents();
    }
    
}
