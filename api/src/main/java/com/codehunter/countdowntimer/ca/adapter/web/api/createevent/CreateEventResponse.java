package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateEventResponse {
    public static final String TEMPLATE = """
             {
                "id": "%d",
                "title": "%s",
                "time": "%s"
            }""";
    private Long id;
    private String title;
    private ZonedDateTime time;
}
