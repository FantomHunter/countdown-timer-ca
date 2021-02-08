package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.getallevent.GetAllEventResponse;
import com.codehunter.countdowntimer.ca.domain.Event;

import org.springframework.stereotype.Component;

@Component
public class GetAllEventConverter {
    GetAllEventResponse.EventResponse convertToGetAllEventResponse(Event event) {
        GetAllEventResponse.EventResponse result = new GetAllEventResponse.EventResponse();
        result.setId(event.getId().getValue());
        result.setTime(event.getDate());
        result.setTitle(event.getName());
        return result;
    }
}
