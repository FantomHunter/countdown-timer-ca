package com.codehunter.countdowntimer.ca;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventRequest;
import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.deleteevent.DeleteEventResponse;
import com.codehunter.countdowntimer.ca.adapter.web.api.getallevent.GetAllEventResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateEventIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void createEvent() {
        String body = String.format(CreateEventRequest.TEMPLATE, "birthday", "2020-12-22");
        ResponseEntity<CreateEventResponse> response = whenCreateEvent(body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private ResponseEntity<CreateEventResponse> whenCreateEvent(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        ResponseEntity<CreateEventResponse> responseEntity = restTemplate.exchange("/event",
                HttpMethod.POST,
                request,
                CreateEventResponse.class);
        return responseEntity;
    }

    @Test
    @Order(2)
    void getAllEvent() {
        HttpEntity<String> request = new HttpEntity<>("", new HttpHeaders());
        ResponseEntity<GetAllEventResponse> responseEntity = restTemplate.exchange("/event",
                HttpMethod.GET,
                request,
                GetAllEventResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getEvents().size());
    }

    @Test
    @Order(3)
    void deleteEvent() {
        HttpEntity<String> request = new HttpEntity<>("", new HttpHeaders());
        ResponseEntity<DeleteEventResponse> responseEntity = restTemplate.exchange("/event/1",
                HttpMethod.DELETE,
                request,
                DeleteEventResponse.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
