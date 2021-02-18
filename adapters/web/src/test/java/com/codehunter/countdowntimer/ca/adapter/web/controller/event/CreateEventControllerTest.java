package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventRequest;
import com.codehunter.countdowntimer.ca.core.port.in.event.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.service.event.CreateEventService;
import com.codehunter.countdowntimer.ca.domain.Event;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateEventControllerTest {

    private final ICreateEventUseCase createEventUseCase = Mockito.mock(CreateEventService.class);
    private final CreateEventController createEventController = new CreateEventController(createEventUseCase, new CreateEventConverter());
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.createEventController).build();

    @Test
    void createEvent_withAllValidInput_status200AndDataIsPassedToService() throws Exception {
        when(createEventUseCase.createEvent(any(ICreateEventUseCase.CreateEventIn.class)))
                .thenReturn(Event.withId(new Event.EventId(1L),"title", new Date()));

        mockMvc.perform(post("/event")
                .content(String.format(CreateEventRequest.TEMPLATE, "test","2020-12-22"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        then(createEventUseCase).should()
                .createEvent(eq(new ICreateEventUseCase.CreateEventIn("test",
                        formatter.parse("2020-12-22"))));
    }

    @Test
    void createEvent_withoutEventName_status422AndErrorMessage() throws Exception {
        mockMvc.perform(post("/event")
                .content("{\n" +
                        "    \"eventTime\": \"2020-12-22\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError())
                .andExpect(status().reason("Invalid input"));
    }

    @Test
    void createEvent_withoutDate_status422AndErrorMessage() throws Exception {
        mockMvc.perform(post("/event")
                .content("{\n" +
                        "    \"name\": \"test\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError())
                .andExpect(status().reason("Invalid input"));
    }
}
