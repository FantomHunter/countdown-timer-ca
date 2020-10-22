package com.codehunter.countdowntimer.ca.adapter.web.api.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class CreateEventWebDataIn {
    private String name;
    private Date eventTime;
}
