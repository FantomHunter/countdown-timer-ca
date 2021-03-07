package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetAllEventControllerTest {
    private final IGetAllEventUseCase getAllEventUseCase = Mockito.mock(IGetAllEventUseCase.class);
    private final GetAllEventController getAllEventController = new GetAllEventController(getAllEventUseCase, new GetAllEventConverter());
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.getAllEventController).build();

    @Test
    void getAllEvent_thenDataIsPassedToService() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/event"));
        then(getAllEventUseCase).should().getAllEvent();
    }

    @Test
    void getAllEvent_withUserCaseReturnData_thenReturnData() throws Exception {
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        List<Event> eventList = Collections.singletonList(Event.withId(new Event.EventId(1L), "Event name", eventTime));
        when(getAllEventUseCase.getAllEvent()).thenReturn(eventList);

        mockMvc.perform(MockMvcRequestBuilders.get("/event"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.events[0].id").value("1"))
                .andExpect(jsonPath("$.events[0].title").value("Event name"))
                .andExpect(jsonPath("$.events[0].time").value(eventTime.getTime()));
    }


}
