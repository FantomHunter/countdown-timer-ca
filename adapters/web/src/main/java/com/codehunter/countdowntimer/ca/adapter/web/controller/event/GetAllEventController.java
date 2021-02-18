package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import java.util.List;
import java.util.stream.Collectors;

import com.codehunter.countdowntimer.ca.adapter.web.api.getallevent.GetAllEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.getallevent.IGetAllEventApi;
import com.codehunter.countdowntimer.ca.core.port.in.event.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.WebAdapter;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@WebAdapter
@RestController
@AllArgsConstructor
public class GetAllEventController implements IGetAllEventApi {
    private final IGetAllEventUseCase getAllEventUseCase;
    private final GetAllEventConverter getAllEventConverter;

    @Override
    public GetAllEventResponse getAllEvent() {
        GetAllEventResponse response = new GetAllEventResponse();
        List<Event> events = getAllEventUseCase.getAllEvent();
        response.setEvents(events.stream()
            .map(event -> getAllEventConverter.convertToGetAllEventResponse(event))
            .collect(Collectors.toList()));
        return response;
    }
    
}
