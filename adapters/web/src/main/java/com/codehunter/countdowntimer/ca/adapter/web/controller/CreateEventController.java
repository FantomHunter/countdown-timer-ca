package com.codehunter.countdowntimer.ca.adapter.web.controller;

import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import com.sun.tools.javac.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sun.security.validator.ValidatorException;

@WebAdapter
@RestController
@RequiredArgsConstructor
@Slf4j
public class CreateEventController {
    private final CreateEventUseCase createEventUseCase;

    @PostMapping(path = "/event")
    @ResponseBody
    void createEvent(@RequestBody CreateEventWebDataIn createEventWebDataIn) throws ValidatorException {
        log.info("Create new event {} ", createEventWebDataIn);
        try {
            CreateEventUseCase.CreateEventIn in = new CreateEventUseCase.CreateEventIn(
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
