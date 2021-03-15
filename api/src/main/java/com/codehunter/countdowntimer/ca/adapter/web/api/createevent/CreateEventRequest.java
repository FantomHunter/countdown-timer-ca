package com.codehunter.countdowntimer.ca.adapter.web.api.createevent;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
