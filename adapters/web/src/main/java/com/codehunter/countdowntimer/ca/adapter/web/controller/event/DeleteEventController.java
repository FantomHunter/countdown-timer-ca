package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.DeleteEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.IDeleteEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.controller.util.OauthUserUtil;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventWithUserUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@Slf4j
public class DeleteEventController implements IDeleteEventApi {
    private final IDeleteEventUseCase deleteEventService;
    private final IDeleteEventWithUserUseCase deleteEventWithUserUseCase;
    private final OauthUserUtil oauthUserUtil;

    @Override
    public DeleteEventResponse deleteEvent(String id) {
        log.info("Delte event with id {}", id);
        DeleteEventResponse response = new DeleteEventResponse();
        if (oauthUserUtil.hasAnyRole("ROLE_ADMIN")) {
            String result = deleteEventService.deleteEvent(new IDeleteEventUseCase.DeleteEventIn(Long.parseLong(id)));
            if (IDeleteEventUseCase.DELETE_EVENT_SUCCESS.equals(result)) {
                response.setResult(DeleteEventResponse.DELETE_SUCCESS);
                return response;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DeleteEventResponse.DELETE_FAIL);
        } else if (oauthUserUtil.getUserFromJwtPrincipal().isPresent()
                && oauthUserUtil.hasAnyRole("ROLE_USER")) {
            String result = deleteEventWithUserUseCase.deleteEventWithUser(
                    new IDeleteEventWithUserUseCase.DeleteEventWithUserIn(
                            Long.parseLong(id), oauthUserUtil.getUserFromJwtPrincipal().get()));
            if (IDeleteEventWithUserUseCase.DELETE_EVENT_WITH_USER_SUCCESS.equals(result)) {
                response.setResult(DeleteEventResponse.DELETE_SUCCESS);
                return response;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DeleteEventResponse.DELETE_FAIL);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DeleteEventResponse.DELETE_FAIL);
    }
}
