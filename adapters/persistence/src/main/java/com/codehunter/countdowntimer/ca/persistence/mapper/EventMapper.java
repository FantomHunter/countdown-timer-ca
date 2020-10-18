package com.codehunter.countdowntimer.ca.persistence.mapper;

import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.entity.EventStatus;
import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventJpaEntity mapToJpaEntity(CreateEventUseCase.CreateEventIn event){
        return new EventJpaEntity(
                null,
                event.getEventName(),
                event.getEventDate(),
                EventStatus.CREATED
        );
    }

    public Event mapToEvent(EventJpaEntity eventJpaEntity){
        return Event.withId(
                new Event.EventId(eventJpaEntity.getId()),
                eventJpaEntity.getName(),
                eventJpaEntity.getPublicTime());
    }
}
