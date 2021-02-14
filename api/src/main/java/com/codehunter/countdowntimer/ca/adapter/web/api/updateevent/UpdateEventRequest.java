package com.codehunter.countdowntimer.ca.adapter.web.api.updateevent;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class UpdateEventRequest {
    public static final String TEMPLATE =  " {\n" +
            "    \"title\": \"%s\",\n" +
            "    \"time\": \"%s\"\n" +
            "}";
    private String title;
    private Date time;
}
