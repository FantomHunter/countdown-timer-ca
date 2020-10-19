package com.codehunter.countdowntimer.ca.adapter.web.controller;

import com.codehunter.countdowntimer.ca.core.port.in.CreateEventUseCase;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreateEventController.class)
public class CreateEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateEventUseCase createEventUseCase;

    @Test
    void createEvent() throws Exception {
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
