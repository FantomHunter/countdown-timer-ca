package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.IUpdateEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.controller.util.OauthUserUtil;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@WebAdapter
@AllArgsConstructor
@RestController
@Slf4j
public class UpdateEventController implements IUpdateEventApi {
    private final IUpdateEventUseCase updateEventUseCase;
    private final IUpdateEventWithUserUseCase updateEventWithUserUseCase;
    private final OauthUserUtil oauthUserUtil;

    @Override
    public UpdateEventResponse updateEvent(String id, UpdateEventRequest eventRequest) {
        log.info("Update event with id {}", id);
        UpdateEventResponse response = new UpdateEventResponse();
        if (oauthUserUtil.hasAnyRole("ROLE_ADMIN")) {
            String result = updateEventUseCase.updateEvent(new IUpdateEventUseCase.UpdateEventIn(
                    Long.parseLong(id),
                    eventRequest.getTitle(),
                    eventRequest.getTime()
            ));

            if (IUpdateEventUseCase.UPDATE_EVENT_SUCCESS.equals(result)) {
                response.setResult(UpdateEventResponse.UPDATE_SUCCESS);
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UpdateEventResponse.UPDATE_FAIL);
            }
        } else if (oauthUserUtil.getUserFromJwtPrincipal().isPresent()
                && oauthUserUtil.hasAnyRole("ROLE_USER")) {

            String result = updateEventWithUserUseCase.updateEventWithUser(
                    new IUpdateEventWithUserUseCase.UpdateEventWithUserIn(
                            Long.parseLong(id),
                            eventRequest.getTitle(),
                            eventRequest.getTime(),
                            oauthUserUtil.getUserFromJwtPrincipal().get()
                    )
            );
            if (IUpdateEventWithUserUseCase.UPDATE_EVENT_WITH_USER_SUCCESS.equals(result)) {
                response.setResult(UpdateEventResponse.UPDATE_SUCCESS);
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UpdateEventResponse.UPDATE_FAIL);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, UpdateEventResponse.UPDATE_FAIL);
    }
}
