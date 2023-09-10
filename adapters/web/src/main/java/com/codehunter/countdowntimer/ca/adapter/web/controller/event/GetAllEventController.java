package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.getallevent.GetAllEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.getallevent.IGetAllEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.controller.util.OauthUserUtil;
import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebAdapter
@RestController
@AllArgsConstructor
@Slf4j
public class GetAllEventController implements IGetAllEventApi {
    private final IGetAllEventUseCase getAllEventUseCase;
    private final IGetAllEventWithUserUseCase getAllEventWithUserUseCase;
    private final GetAllEventConverter getAllEventConverter;
    private final OauthUserUtil oauthUserUtil;

    @Override
    public GetAllEventResponse getAllEvent() {
        log.info("Get all event controller");
        GetAllEventResponse response = new GetAllEventResponse();
        List<Event> events = new ArrayList<>();
        if (oauthUserUtil.hasAnyRole("ROLE_ADMIN")) {
            List<Event> allEvent = getAllEventUseCase.getAllEvent();
            events.addAll(allEvent);
        } else if (oauthUserUtil.getUserFromJwtPrincipal().isPresent()
                && oauthUserUtil.hasAnyRole("ROLE_USER")) {
            List<Event> allEventWithUser = getAllEventWithUserUseCase.getAllEventWithUser(
                    oauthUserUtil.getUserFromJwtPrincipal().get()
            );
            events.addAll(allEventWithUser);
        }
        response.setEvents(events.stream()
                .map(getAllEventConverter::convertToGetAllEventResponse)
                .collect(Collectors.toList()));
        return response;
    }

}
