package com.codehunter.countdowntimer.ca.persistence.mapper;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.entity.EventStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventMapper {
    private final UserMapper usermapper;

    public EventJpaEntity mapToJpaEntity(ICreateEventUseCase.CreateEventIn event) {
        return new EventJpaEntity(
                null,
                event.getEventName(),
                event.getEventDate(),
                EventStatus.CREATED,
                null
        );
    }

    public EventJpaEntity mapToJpaEntity(ICreateEventWithUserUseCase.CreateEventWithUserIn event) {
        return new EventJpaEntity(null,
                event.getEventName(),
                event.getEventDate(),
                EventStatus.CREATED,
                usermapper.mapToJpaEntity(event.getUser()));
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
                EventStatus.UPDATED,
                null
        );
    }

    public EventJpaEntity mapToJpaEntity(IUpdateEventWithUserUseCase.UpdateEventWithUserIn event) {
        return new EventJpaEntity(
                event.getId(),
                event.getName(),
                event.getTime(),
                EventStatus.UPDATED,
                usermapper.mapToJpaEntity(event.getUser())
        );
    }
}
