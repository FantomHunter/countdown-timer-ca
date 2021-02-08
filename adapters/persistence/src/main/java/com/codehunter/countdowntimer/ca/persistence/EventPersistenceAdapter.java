package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.EventMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.EventRepository;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class EventPersistenceAdapter implements ICreateEventPort, IGetAllEventPort {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event createEvent(ICreateEventUseCase.CreateEventIn event) {
        EventJpaEntity in = eventMapper.mapToJpaEntity(event);
        EventJpaEntity out = eventRepository.save(in);
        return eventMapper.mapToEvent(out);
    }

    @Override
    public List<Event> getAllEvents() {
        List<EventJpaEntity> out =  eventRepository.findAll();
        if (!CollectionUtils.isEmpty(out)) {
            return out.stream().map(item ->eventMapper.mapToEvent(item)).collect(Collectors.toList());
        }
        return List.of();
    }
}
