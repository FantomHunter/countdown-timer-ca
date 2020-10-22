package com.codehunter.countdowntimer.ca.adapter.web.api;

import com.codehunter.countdowntimer.ca.adapter.web.api.payload.request.CreateEventWebDataIn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ICreateEventApi {

    @PostMapping(path = "/event")
    @ResponseBody
    void createEvent(@RequestBody CreateEventWebDataIn createEventWebDataIn);
}
