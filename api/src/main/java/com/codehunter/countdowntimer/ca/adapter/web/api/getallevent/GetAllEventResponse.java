package com.codehunter.countdowntimer.ca.adapter.web.api.getallevent;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class GetAllEventResponse {

    private List<EventResponse> events;

    /**
     * EventResponse
     */
    @Data
	public static class EventResponse {

        public static final String TEMPLATE = """
                 {
                    "id": "%d",
                    "title": "%s",
                    "time": "%s"
                }""";
        private Long id;
        private String title;
        private ZonedDateTime time;
    }

}
