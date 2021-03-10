package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.IUpdateEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.controller.util.OauthUserUtil;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UpdateEventControllerTest {
    private final IUpdateEventUseCase updateEventUseCase = Mockito.mock(IUpdateEventUseCase.class);
    private final IUpdateEventWithUserUseCase updateEventWithUserUseCase = Mockito.mock(IUpdateEventWithUserUseCase.class);
    private final OauthUserUtil oauthUserUtil = Mockito.mock(OauthUserUtil.class);
    private final IUpdateEventApi updateEventController = new UpdateEventController(updateEventUseCase, updateEventWithUserUseCase, oauthUserUtil);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.updateEventController).build();

    @Test
    void updateEvent_withAdminRoleWithValidEvent_thenReturnSuccess() throws Exception {
        when(updateEventUseCase.updateEvent(any())).thenReturn(IUpdateEventUseCase.UPDATE_EVENT_SUCCESS);
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(true);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(UpdateEventRequest.TEMPLATE, "update event", "2020-12-22"))
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(UpdateEventResponse.UPDATE_SUCCESS));

    }

    @Test
    void updateEvent_withAdminRoleWithInvalidEvent_thenReturnFailResponse() throws Exception {
        when(updateEventUseCase.updateEvent(any())).thenReturn(IUpdateEventUseCase.UPDATE_EVENT_NOT_EXIST);
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(true);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/event/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(UpdateEventRequest.TEMPLATE, "update event", "2020-12-22"))
        ).andExpect(status().is4xxClientError())
                .andExpect(status().reason(UpdateEventResponse.UPDATE_FAIL));

    }

    @Test
    void updateEvent_withUserRoleWithValidEvent_thenReturnSuccess() throws Exception {
        when(updateEventWithUserUseCase.updateEventWithUser(any())).thenReturn(IUpdateEventWithUserUseCase.UPDATE_EVENT_WITH_USER_SUCCESS);
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(false);
        when(oauthUserUtil.hasAnyRole("ROLE_USER")).thenReturn(true);
        User user = User.withId("id", "name");
        when(oauthUserUtil.getUserFromJwtPrincipal()).thenReturn(Optional.of(user));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(UpdateEventRequest.TEMPLATE, "update event", "2020-12-22"))
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(UpdateEventResponse.UPDATE_SUCCESS));

    }

    @Test
    void updateEvent_withUserRoleWithInvalidEvent_thenReturnFailResponse() throws Exception {
        when(updateEventWithUserUseCase.updateEventWithUser(any())).thenReturn(IUpdateEventWithUserUseCase.UPDATE_EVENT_WITH_USER_NOT_EXIST);
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(false);
        when(oauthUserUtil.hasAnyRole("ROLE_USER")).thenReturn(true);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/event/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(UpdateEventRequest.TEMPLATE, "update event", "2020-12-22"))
        ).andExpect(status().is4xxClientError())
                .andExpect(status().reason(UpdateEventResponse.UPDATE_FAIL));

    }
}
