package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.EventMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.EventRepository;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class EventPersistenceAdapter implements ICreateEventPort {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event createEvent(ICreateEventUseCase.CreateEventIn event) {
        EventJpaEntity in = eventMapper.mapToJpaEntity(event);
        EventJpaEntity out = eventRepository.save(in);
        return eventMapper.mapToEvent(out);
    }
}
