package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.core.port.out.IDeleteEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasEventPort;
import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.EventMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.ICreateEventPort;
import com.codehunter.countdowntimer.ca.core.port.out.IGetAllEventPort;
import com.codehunter.countdowntimer.ca.domain.Event;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@PersistenceAdapter
@Slf4j
public class EventPersistenceAdapter implements ICreateEventPort, IGetAllEventPort, IDeleteEventPort, IHasEventPort {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event createEvent(ICreateEventUseCase.CreateEventIn event) {
        log.info("CreateEvent {}", event);
        EventJpaEntity in = eventMapper.mapToJpaEntity(event);
        EventJpaEntity out = eventRepository.save(in);
        return eventMapper.mapToEvent(out);
    }

    @Override
    public List<Event> getAllEvents() {
        log.info("Get All Event");
        List<EventJpaEntity> out = eventRepository.findAll();
        if (!CollectionUtils.isEmpty(out)) {
            return out.stream().map(item -> eventMapper.mapToEvent(item)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteEvent(Long eventId) {
        log.info("Delete Event {}", eventId);
        EventJpaEntity deletedEvent = eventRepository.getOne(eventId);
        eventRepository.delete(deletedEvent);
    }

    @Override
    public boolean hasEvent(Long eventId) {
        log.info("Check Has Event with Id {}", eventId);
        return eventRepository.existsById(eventId);
    }
}
