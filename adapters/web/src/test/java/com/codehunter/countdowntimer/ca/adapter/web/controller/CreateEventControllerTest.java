package com.codehunter.countdowntimer.ca.adapter.web.controller;

import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.service.CreateEventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateEventControllerTest {

    private final CreateEventUseCase createEventUseCase = Mockito.mock(CreateEventService.class);
    private CreateEventController createEventController = new CreateEventController(createEventUseCase);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.createEventController).build();

    @Test
    void createEvent_withAllValidInput_status200AndDataIsPassedToService() throws Exception {
        mockMvc.perform(post("/event")
                .content(" {\n" +
                        "    \"name\": \"test\",\n" +
                        "    \"eventTime\": \"2020-12-22\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        then(createEventUseCase).should()
                .createEvent(eq(new CreateEventUseCase.CreateEventIn("test",
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
