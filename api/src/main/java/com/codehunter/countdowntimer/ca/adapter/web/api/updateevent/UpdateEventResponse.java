package com.codehunter.countdowntimer.ca.adapter.web.api.updateevent;

import lombok.Data;

@Data
public class UpdateEventResponse {
    public static final String UPDATE_SUCCESS = "Event updated";
    public static final String UPDATE_FAIL = "Cannot update event";
    private String result;
}
