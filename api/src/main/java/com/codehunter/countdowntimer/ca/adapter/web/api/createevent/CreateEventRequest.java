package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
public class CreateEventRequest {
    public static final String TEMPLATE =  " {\n" +
            "    \"title\": \"%s\",\n" +
            "    \"time\": \"%s\"\n" +
            "}";
    @NotEmpty
    private String title;
    @NotEmpty
    private Date time;
}
