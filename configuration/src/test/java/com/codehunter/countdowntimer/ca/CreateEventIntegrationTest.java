package com.codehunter.countdowntimer.ca;

import com.codehunter.countdowntimer.ca.adapter.web.api.createevent.CreateEventRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.BDDAssertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateEventIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createEvent() {
        String body = String.format(CreateEventRequest.TEMPLATE, "birthday", "2020-12-22");
        ResponseEntity<Object> response = whenCreateEvent(body);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<Object> whenCreateEvent(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange("/event",
                HttpMethod.POST,
                request,
                Object.class);
        return responseEntity;
    }
}
