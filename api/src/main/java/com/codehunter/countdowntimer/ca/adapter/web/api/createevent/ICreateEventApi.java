package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventWebDataIn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ICreateEventApi {

    @PostMapping(path = "/event")
    @ResponseBody
    void createEvent(@RequestBody CreateEventWebDataIn createEventWebDataIn);
}
