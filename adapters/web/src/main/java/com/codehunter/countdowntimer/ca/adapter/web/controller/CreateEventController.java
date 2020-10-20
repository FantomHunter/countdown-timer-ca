package com.codehunter.countdowntimer.ca.adapter.web.controller;

import com.codehunter.countdowntimer.ca.adapter.web.controller.payload.request.CreateEventWebDataIn;
import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@WebAdapter
@RestController
@RequiredArgsConstructor
@Slf4j
public class CreateEventController {
    private final ICreateEventUseCase createEventUseCase;

    @PostMapping(path = "/event")
    @ResponseBody
    void createEvent(@RequestBody CreateEventWebDataIn createEventWebDataIn){
        log.info("Create new event {} ", createEventWebDataIn);
        try {
            ICreateEventUseCase.CreateEventIn in = new ICreateEventUseCase.CreateEventIn(
                    createEventWebDataIn.getName(),
                    createEventWebDataIn.getEventTime()
            );
            createEventUseCase.createEvent(in);
        } catch (NullPointerException e) {
            log.error("cannot create event");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid input",
                    e);
        }
    }
}
