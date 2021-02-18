package com.codehunter.countdowntimer.ca.persistence.mapper;

import com.codehunter.countdowntimer.ca.core.port.in.event.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.entity.EventStatus;
import com.codehunter.countdowntimer.ca.core.port.in.event.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventJpaEntity mapToJpaEntity(ICreateEventUseCase.CreateEventIn event) {
        return new EventJpaEntity(
                null,
                event.getEventName(),
                event.getEventDate(),
                EventStatus.CREATED
        );
    }

    public Event mapToEvent(EventJpaEntity eventJpaEntity) {
        return Event.withId(
                new Event.EventId(eventJpaEntity.getId()),
                eventJpaEntity.getName(),
                eventJpaEntity.getPublicTime());
    }

    public EventJpaEntity mapToJpaEntity(IUpdateEventUseCase.UpdateEventIn event) {
        return new EventJpaEntity(
                event.getId(),
                event.getName(),
                event.getTime(),
                EventStatus.UPDATED
        );
    }
}
