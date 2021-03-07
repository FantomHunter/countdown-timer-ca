package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.*;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.EventMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@PersistenceAdapter
@Slf4j
public class EventPersistenceAdapter implements ICreateEventPort, IGetAllEventPort, IDeleteEventPort, IHasEventPort,
        IUpdateEventPort {
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
    public Event createEvent(ICreateEventWithUserUseCase.CreateEventWithUserIn eventWithUserIn) {
        log.info("Create event with user : {}", eventWithUserIn);
        EventJpaEntity in = eventMapper.mapToJpaEntity(eventWithUserIn);
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
    public List<Event> getAllEventsWithUser(User user) {
        log.info("Get all event for user: {}", user);
        List<EventJpaEntity> out = eventRepository.findByUser(user.getId().getValue());
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

    @Override
    public Event updateEvent(IUpdateEventUseCase.UpdateEventIn event) {
        log.info("Update Event {}", event);
        boolean hasEvent =  eventRepository.existsById(event.getId());
        if (hasEvent) {
            EventJpaEntity eventUpdated = eventRepository.save(eventMapper.mapToJpaEntity(event));
            return eventMapper.mapToEvent(eventUpdated);
        }
        throw new EntityNotFoundException("Event not exist");
    }
}
