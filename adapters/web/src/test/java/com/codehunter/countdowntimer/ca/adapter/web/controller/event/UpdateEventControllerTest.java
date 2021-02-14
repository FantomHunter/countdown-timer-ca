package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.IUpdateEventApi;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.api.updateevent.UpdateEventResponse;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UpdateEventControllerTest {
    private final IUpdateEventUseCase updateEventUseCase = Mockito.mock(IUpdateEventUseCase.class);
    private final IUpdateEventApi updateEventController = new UpdateEventController(updateEventUseCase);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.updateEventController).build();

    @Test
    void updateEvent_withValidEvent_thenReturnSuccess() throws Exception {
        when(updateEventUseCase.updateEvent(any())).thenReturn(IUpdateEventUseCase.UPDATE_EVENT_SUCCESS);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/event/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(UpdateEventRequest.TEMPLATE, "update event", "2020-12-22"))
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(UpdateEventResponse.UPDATE_SUCCESS));

    }

    @Test
    void updateEvent_withInvalidEvent_thenReturnFailResponse() throws Exception {
        when(updateEventUseCase.updateEvent(any())).thenReturn(IUpdateEventUseCase.UPDATE_EVENT_NOT_EXIST);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/event/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(UpdateEventRequest.TEMPLATE, "update event", "2020-12-22"))
        ).andExpect(status().is4xxClientError())
                .andExpect(status().reason(UpdateEventResponse.UPDATE_FAIL));

    }

}
