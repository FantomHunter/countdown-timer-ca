package com.codehunter.countdowntimer.ca.adapter.web.api.updateevent;

import org.springframework.web.bind.annotation.*;

public interface IUpdateEventApi {
    @PutMapping(path = "/event/{id}")
    UpdateEventResponse updateEvent(@PathVariable String id, @RequestBody UpdateEventRequest eventRequest);
}
