package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventResponse;
import com.codehunter.countdowntimer.ca.domain.Event;

import org.springframework.stereotype.Component;

@Component
public class CreateEventConverter {
    CreateEventResponse convertToCreateEventResponse(Event event) {
        CreateEventResponse result = new CreateEventResponse();
        result.setId(event.getId().getValue());
        result.setTime(event.getDate());
        result.setTitle(event.getName());
        return result;
    }
}
