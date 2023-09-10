package com.codehunter.countdowntimer.ca.adapter.web.api.updateevent;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UpdateEventRequest {
    public static final String TEMPLATE = """
             {
                "title": "%s",
                "time": "%s"
            }""";
    private String title;
    private ZonedDateTime time;
}
