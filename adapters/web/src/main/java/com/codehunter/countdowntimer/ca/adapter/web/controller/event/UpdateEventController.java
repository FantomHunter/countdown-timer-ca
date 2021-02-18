package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.IUpdateEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventResponse;
import com.codehunter.countdowntimer.ca.core.port.in.event.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@WebAdapter
@AllArgsConstructor
@RestController
public class UpdateEventController implements IUpdateEventApi {
    private final IUpdateEventUseCase updateEventUseCase;

    @Override
    public UpdateEventResponse updateEvent(String id, UpdateEventRequest eventRequest) {
        UpdateEventResponse response = new UpdateEventResponse();
        String result = updateEventUseCase.updateEvent(new IUpdateEventUseCase.UpdateEventIn(
                Long.parseLong(id),
                eventRequest.getTitle(),
                eventRequest.getTime()));

        if (IUpdateEventUseCase.UPDATE_EVENT_SUCCESS.equals(result)) {
            response.setResult(UpdateEventResponse.UPDATE_SUCCESS);
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UpdateEventResponse.UPDATE_FAIL);
        }
    }
}
