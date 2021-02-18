package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.DeleteEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.IDeleteEventApi;
import com.codehunter.countdowntimer.ca.core.port.in.event.IDeleteEventUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class DeleteEventControllerTest {
    private final IDeleteEventUseCase deleteEventService = Mockito.mock(IDeleteEventUseCase.class);
    private final IDeleteEventApi deleteEventController = new DeleteEventController(deleteEventService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.deleteEventController).build();

    @Test
    void deleteEvent_withValidId_thenReturnSuccessMessage() throws Exception {
        when(deleteEventService.deleteEvent(any())).thenReturn(IDeleteEventUseCase.DELETE_EVENT_SUCCESS);

        mockMvc.perform(MockMvcRequestBuilders.delete("/event/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value(DeleteEventResponse.DELETE_SUCCESS));
    }

    @Test
    void deleteEvent_withInvalidId_thenReturnFailMessage() throws Exception {
        when(deleteEventService.deleteEvent(any())).thenReturn(IDeleteEventUseCase.DELETE_EVENT_NOT_EXIST);
        mockMvc.perform(MockMvcRequestBuilders.delete("/event/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(status().reason(DeleteEventResponse.DELETE_FAIL));
    }


}
