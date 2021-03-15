package com.codehunter.countdowntimer.ca.adapter.web.api.getallevent;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GetAllEventResponse {

    private List<EventResponse> events;

    /**
     * EventResponse
     */
    @Data
	public static class EventResponse {

        public static final String TEMPLATE = " {\n" + "    \"id\": \"%d\",\n" + "    \"title\": \"%s\",\n"
                + "    \"time\": \"%s\"\n" + "}";
        private Long id;
        private String title;
        private Date time;
    }

}
