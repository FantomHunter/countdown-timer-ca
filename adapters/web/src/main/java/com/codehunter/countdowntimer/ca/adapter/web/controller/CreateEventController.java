package com.codehunter.countdowntimer.ca.adapter.web.controller;

import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class CreateEventController {
    private final CreateEventUseCase createEventUseCase;

    @PostMapping(path = "/event")
    @ResponseBody
    void createEvent(@RequestBody CreateEventWebDataIn createEventWebDataIn){
        CreateEventUseCase.CreateEventIn in = new CreateEventUseCase.CreateEventIn(
                createEventWebDataIn.getName(),
                createEventWebDataIn.getEventTime()
        );
        createEventUseCase.createEvent(in);
    }
}
