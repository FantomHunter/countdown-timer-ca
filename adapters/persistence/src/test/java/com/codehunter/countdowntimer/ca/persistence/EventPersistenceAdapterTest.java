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
import java.util.Date;
import static org.assertj.core.api.Assertions.*;

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

}
