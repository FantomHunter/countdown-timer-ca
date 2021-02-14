package com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent;

import lombok.Data;

@Data
public class DeleteEventResponse {
    public static final String DELETE_SUCCESS = "Event deleted";
    public static final String DELETE_FAIL = "Cannot delete event";
    private String result;
}
