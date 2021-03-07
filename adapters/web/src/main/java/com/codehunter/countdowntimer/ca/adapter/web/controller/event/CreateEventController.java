package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.ICreateEventApi;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.WebAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@WebAdapter
@RestController
@RequiredArgsConstructor
@Slf4j
public class CreateEventController implements ICreateEventApi {
    private final ICreateEventUseCase createEventUseCase;
    private final ICreateEventWithUserUseCase createEventWithUserUseCase;
    private final CreateEventConverter createEventConverter;

    @Override
    public CreateEventResponse createEvent(CreateEventRequest createEventRequest) {
        log.info("Create new event {} ", createEventRequest);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Collection<? extends GrantedAuthority> listAuth = auth.getAuthorities();
            if (listAuth.stream().anyMatch(role -> "ROLE_ADMIN".equalsIgnoreCase(role.getAuthority()))) {
                ICreateEventUseCase.CreateEventIn in = new ICreateEventUseCase.CreateEventIn(
                        createEventRequest.getTitle(),
                        createEventRequest.getTime()
                );
                Event event = createEventUseCase.createEvent(in);
                return createEventConverter.convertToCreateEventResponse(event);
            } else if (listAuth.stream().anyMatch(role -> "ROLE_USER".equalsIgnoreCase(role.getAuthority()))
                    && auth.getPrincipal() instanceof Jwt) {
                Jwt principal = (Jwt) auth.getPrincipal();
                String userId = principal.getClaimAsString("sub");
                String username = principal.getClaimAsString("preferred_username");
                log.info("request with userID: {}", userId);
                ICreateEventWithUserUseCase.CreateEventWithUserIn in = new ICreateEventWithUserUseCase.CreateEventWithUserIn(
                        User.withId(userId, username),
                        createEventRequest.getTitle(),
                        createEventRequest.getTime()
                );
                Event event = createEventWithUserUseCase.createEvent(in);
                return createEventConverter.convertToCreateEventResponse(event);
            }
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid input");
        } catch (NullPointerException e) {
            log.error("cannot create event");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid input",
                    e);
        }

    }
}
