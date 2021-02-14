package com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

public interface IDeleteEventApi {

    @DeleteMapping(path = "/event/{id}")
    @ResponseBody
    DeleteEventResponse deleteEvent(@PathVariable String id);
}
