package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import lombok.Data;

import java.util.Date;

@Data
public class CreateEventResponse {
    public static final String TEMPLATE =  " {\n" +
            "    \"id\": \"%d\",\n" +
            "    \"title\": \"%s\",\n" +
            "    \"time\": \"%s\"\n" +
            "}";
    private Long id;
    private String title;
    private Date time;
}
