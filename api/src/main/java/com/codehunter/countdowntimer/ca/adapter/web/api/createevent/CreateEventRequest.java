package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import lombok.Data;

import java.util.Date;

@Data
public class CreateEventRequest {
    public static final String TEMPLATE =  " {\n" +
            "    \"title\": \"%s\",\n" +
            "    \"time\": \"%s\"\n" +
            "}";
    private String title;
    private Date time;
}
