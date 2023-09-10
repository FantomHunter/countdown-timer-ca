package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateEventRequest {
    public static final String TEMPLATE = """
             {
                "title": "%s",
                "time": "%s"
            }""";
    @NotEmpty
    private String title;
    @NotEmpty
    private ZonedDateTime time;
}
