package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.domain.Event;
import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.mapper.EventMapper;
import com.codehunter.countdowntimer.ca.persistence.mapper.UserMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EventPersistenceAdapter.class, EventMapper.class, UserMapper.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    void getAllEvent_shouldReturn1Event() {
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        List<Event> expected = Collections.singletonList(Event.withId(new Event.EventId(1L), "event from sql unit test", eventTime));
        List<Event> actual = adapterUnderTest.getAllEvents();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void deleteEvent_withValidId_shouldDeleteSuccessfully() {
        adapterUnderTest.deleteEvent(1L);
        List<Event> allEvents = adapterUnderTest.getAllEvents();
        assertEquals(0, allEvents.size());
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void deleteEvent_withInvalidId_thenThrowEntityNotFoundException() {
        try {
            adapterUnderTest.deleteEvent(5L);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
        }
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void hasEvent_withValidId_thenReturnTrue() {
        boolean actual = adapterUnderTest.hasEvent(1L);
        assertTrue(actual);
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void hasEvent_withInValidId_thenReturnFalse() {
        boolean actual = adapterUnderTest.hasEvent(5L);
        assertFalse(actual);
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void updateEvent_withNotExistEvent_thenReturnException() {
        Date updateTime = new GregorianCalendar(2021, Calendar.AUGUST, 15).getTime();
        IUpdateEventUseCase.UpdateEventIn input = new IUpdateEventUseCase.UpdateEventIn(5L, "event update", updateTime);
        try {
            adapterUnderTest.updateEvent(input);
        } catch (Exception e) {
            assertEquals(EntityNotFoundException.class, e.getClass());
        }
    }

    @Test
    @Sql("EventPersistenceAdapterTest.sql")
    void updateEvent_withValidEvent_thenReturnUpdatedEvent() {
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        IUpdateEventUseCase.UpdateEventIn input = new IUpdateEventUseCase.UpdateEventIn(1L, "event updated", eventTime);
        Event actual = adapterUnderTest.updateEvent(input);

        Event expected = Event.withId(new Event.EventId(1L), "event updated", eventTime);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected, actual);

    }

    @Test
    @Sql("EventPersistenceAdapterTestV2.sql")
    void createEvent_withValidInput_thenReturnNewEvent() {
        Date eventTime = new GregorianCalendar(2020, Calendar.OCTOBER, 18, 16, 0, 0).getTime();
        User userIn = User.withId("user-id", "user name");
        ICreateEventWithUserUseCase.CreateEventWithUserIn input = new ICreateEventWithUserUseCase.CreateEventWithUserIn(userIn, "event name", eventTime);
        Event actual = adapterUnderTest.createEvent(input);

        Event expected = Event.withId(new Event.EventId(3L), "event name", eventTime);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected, actual);

    }

    @Test
    @Sql("EventPersistenceAdapterTestV2.sql")
    void getAllEventWithUser_withValidInput_thenReturn1Event() {
        List<Event> actual = adapterUnderTest.getAllEventsWithUser(
                User.withId("first-user-id", "don't care"));
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals("event 1 from sql unit test", actual.get(0).getName());
    }

    @Test
    @Sql("EventPersistenceAdapterTestV2.sql")
    void getAllEventWithNonExistedUser_withValidInput_thenReturnEmptyEventList() {
        List<Event> actual = adapterUnderTest.getAllEventsWithUser(
                User.withId("non-existed-user-id", "don't care"));
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

}
