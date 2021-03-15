package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.DeleteEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.IDeleteEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.controller.util.OauthUserUtil;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventWithUserUseCase;
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


public class DeleteEventControllerTest {
    private final IDeleteEventUseCase deleteEventService = Mockito.mock(IDeleteEventUseCase.class);
    private final IDeleteEventWithUserUseCase deleteEventWithUserUseCase = Mockito.mock(IDeleteEventWithUserUseCase.class);
    private final OauthUserUtil oauthUserUtil = Mockito.mock(OauthUserUtil.class);
    private final IDeleteEventApi deleteEventController = new DeleteEventController(deleteEventService, deleteEventWithUserUseCase, oauthUserUtil);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.deleteEventController).build();

    @Test
    void deleteEvent_withAdminRoleWithValidId_thenReturnSuccessMessage() throws Exception {
        when(deleteEventService.deleteEvent(any())).thenReturn(IDeleteEventUseCase.DELETE_EVENT_SUCCESS);
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/event/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(DeleteEventResponse.DELETE_SUCCESS));
    }

    @Test
    void deleteEvent_withAdminRoleWithInvalidId_thenReturnFailMessage() throws Exception {
        when(deleteEventService.deleteEvent(any())).thenReturn(IDeleteEventUseCase.DELETE_EVENT_NOT_EXIST);
        mockMvc.perform(MockMvcRequestBuilders.delete("/event/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(DeleteEventResponse.DELETE_FAIL));
    }

    @Test
    void deleteEvent_withUserRoleWithValidId_thenReturnSuccessMessage() throws Exception {
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(false);
        when(oauthUserUtil.hasAnyRole("ROLE_USER")).thenReturn(true);
        User user = User.withId("id", "name");
        when(oauthUserUtil.getUserFromJwtPrincipal()).thenReturn(Optional.of(user));
        when(deleteEventWithUserUseCase.deleteEventWithUser(any())).thenReturn(IDeleteEventWithUserUseCase.DELETE_EVENT_WITH_USER_SUCCESS);

        mockMvc.perform(MockMvcRequestBuilders.delete("/event/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(DeleteEventResponse.DELETE_SUCCESS));
    }

    @Test
    void deleteEvent_withUserRoleWithInvalidId_thenReturnFailMessage() throws Exception {
        when(oauthUserUtil.hasAnyRole("ROLE_ADMIN")).thenReturn(false);
        when(oauthUserUtil.hasAnyRole("ROLE_USER")).thenReturn(true);
        User user = User.withId("id", "name");
        when(deleteEventWithUserUseCase.deleteEventWithUser(any())).thenReturn(IDeleteEventWithUserUseCase.DELETE_EVENT_WITH_USER_NOT_EXIST);
        mockMvc.perform(MockMvcRequestBuilders.delete("/event/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(DeleteEventResponse.DELETE_FAIL));
    }

}
