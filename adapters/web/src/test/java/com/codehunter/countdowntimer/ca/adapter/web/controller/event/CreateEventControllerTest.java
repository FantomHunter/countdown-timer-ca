package com.codehunter.countdowntimer.ca.adapter.web.controller.event;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.controller.config.SpringSecurityWebTestConfig;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateEventControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ICreateEventUseCase createEventUseCase;
    @Autowired
    private ICreateEventWithUserUseCase createEventWithUserUseCase;
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
//    @WithMockUser(username = "test", roles = SpringSecurityWebTestConfig.ROLE_ADMIN)
    void createEvent_withAdminAccount_thenStatus200AndDataIsPassedToService() throws Exception {
        when(createEventUseCase.createEvent(any(ICreateEventUseCase.CreateEventIn.class)))
                .thenReturn(Event.withId(new Event.EventId(1L), "title", ZonedDateTime.now()));

        mockMvc.perform(post("/event")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringSecurityWebTestConfig.AUTH0_TOKEN_ADMIN)
                        .content(String.format(CreateEventRequest.TEMPLATE, "test", "2020-12-22T00:00:00.0Z"))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .with(SecurityMockMvcRequestPostProcessors.user("test").roles("ADMIN"))
        ).andExpect(status().isOk());
        ZonedDateTime eventTime = ZonedDateTime.of(LocalDateTime.of(2020, Month.DECEMBER, 22, 0, 0, 0), ZoneOffset.UTC);

        then(createEventUseCase).should()
                .createEvent(eq(new ICreateEventUseCase.CreateEventIn("test",
                        eventTime)));
    }

    @Test
    void createEvent_withoutEventName_status422AndErrorMessage() throws Exception {
        mockMvc.perform(post("/event")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringSecurityWebTestConfig.AUTH0_TOKEN_ADMIN)
                        .content("""
                                {
                                    "eventTime": "2020-12-22"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(status().reason("Invalid input"));
    }

    @Test
    void createEvent_withoutDate_status422AndErrorMessage() throws Exception {
        mockMvc.perform(post("/event")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringSecurityWebTestConfig.AUTH0_TOKEN_ADMIN)
                        .content("""
                                {
                                    "name": "test"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(status().reason("Invalid input"));
    }

    @Test
    void createEvent_withUserAccount_thenStatus200AndDataIsPassedToService() throws Exception {
        when(createEventWithUserUseCase.createEvent(any(ICreateEventWithUserUseCase.CreateEventWithUserIn.class)))
                .thenReturn(Event.withId(new Event.EventId(1L), "title", ZonedDateTime.now()));

        mockMvc.perform(post("/event")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringSecurityWebTestConfig.AUTH0_TOKEN_USER)
                .content(String.format(CreateEventRequest.TEMPLATE, "test", "2020-12-22T00:00:00.0Z"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());

        ZonedDateTime eventTime = ZonedDateTime.of(LocalDateTime.of(2020, Month.DECEMBER, 22, 0, 0, 0), ZoneOffset.UTC);
        then(createEventWithUserUseCase).should()
                .createEvent(eq(new ICreateEventWithUserUseCase.CreateEventWithUserIn(
                        User.withId(SpringSecurityWebTestConfig.USER_AUTH0ID, SpringSecurityWebTestConfig.USERNAME_USER),
                        "test",
                        eventTime)));
    }

    @Test
    void createEvent_withUserAccountWithoutEventName_status422AndErrorMessage() throws Exception {
        mockMvc.perform(post("/event")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringSecurityWebTestConfig.AUTH0_TOKEN_USER)
                        .content("""
                                {
                                    "eventTime": "2020-12-22"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(status().reason("Invalid input"));
    }

    @Test
    void createEvent_withUserAccountWithoutDate_status422AndErrorMessage() throws Exception {
        mockMvc.perform(post("/event")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringSecurityWebTestConfig.AUTH0_TOKEN_USER)
                        .content("""
                                {
                                    "name": "test"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andExpect(status().reason("Invalid input"));
    }
}
