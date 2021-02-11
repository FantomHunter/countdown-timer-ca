package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.persistence.mapper.EventMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({EventPersistenceAdapter.class, EventMapper.class})
public class EventPersistenceAdapterTest {

    @Autowired
    private EventPersistenceAdapter adapterUnderTest;

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void createEvent() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date eventTime = simpleDateFormat.parse("2020-18-10");

        Event event = adapterUnderTest.createEvent(
                new ICreateEventUseCase.CreateEventIn("event unit test", eventTime));
        assertThat(event.getId().getValue()).isEqualTo(2L);
        assertThat(event.getDate()).isEqualTo(eventTime);
        assertThat(event.getName()).isEqualTo("event unit test");
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void getAllEvent_shouldReturn1Event(){
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        List<Event> expected = Collections.singletonList(Event.withId(new Event.EventId(1L), "event from sql unit test", eventTime));
        List<Event> actual = adapterUnderTest.getAllEvents();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
    }


}
