package com.codehunter.countdowntimer.ca.adapter.web.api.getallevent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IGetAllEventApi {

    @GetMapping(path = "/event")
    @ResponseBody
    GetAllEventResponse getAllEvent();
}
