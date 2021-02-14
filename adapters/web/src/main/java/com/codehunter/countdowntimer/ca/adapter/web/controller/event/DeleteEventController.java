package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.DeleteEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.IDeleteEventApi;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class DeleteEventController implements IDeleteEventApi {
    private final IDeleteEventUseCase deleteEventService;

    @Override
    public DeleteEventResponse deleteEvent(String id) {
        DeleteEventResponse response = new DeleteEventResponse();
        String result = deleteEventService.deleteEvent(new IDeleteEventUseCase.DeleteEventIn(Long.parseLong(id)));
        if (IDeleteEventUseCase.DELETE_EVENT_SUCCESS.equals(result)){
            response.setResult(DeleteEventResponse.DELETE_SUCCESS);
            return response;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DeleteEventResponse.DELETE_FAIL);
        }
    }
}
