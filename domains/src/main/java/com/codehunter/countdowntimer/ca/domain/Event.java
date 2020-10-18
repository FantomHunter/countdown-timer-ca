package com.codehunter.countdowntimer.ca.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event {
    @Getter private final EventId id;
    @Getter private final String name;
    @Getter private final Date date;

    public static  Event withoutId(String name, Date date){
        return new Event(null, name, date);
    }

    public static Event withId(EventId eventId,String name, Date date){
        return new Event(eventId, name, date);
    }

    @Value
    public static class EventId {
       private Long value;
    }

}
