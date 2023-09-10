package com.codehunter.countdowntimer.ca.domain;

import lombok.*;

import java.time.ZonedDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Event {
    @Getter
    private final EventId id;
    @Getter
    private final String name;
    @Getter
    private final ZonedDateTime date;

    public static Event withoutId(String name, ZonedDateTime date) {
        return new Event(null, name, date);
    }

    public static Event withId(EventId eventId, String name, ZonedDateTime date) {
        return new Event(eventId, name, date);
    }

    @Value
    public static class EventId {
        Long value;
    }

}
