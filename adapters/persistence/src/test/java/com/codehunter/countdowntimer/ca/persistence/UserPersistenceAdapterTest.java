package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.mapper.UserMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserMapper.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserPersistenceAdapterTest {
    @Autowired
    private UserPersistenceAdapter adapterUnderTest;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void hasUser_withValidInput_thenReturnTrue() {
        boolean actual = adapterUnderTest.hasUser(User.withId("first-user-id", "hunter"));
        assertTrue(actual);
    }

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void hasUser_withInValidInput_thenReturnFalse() {
        boolean actual = adapterUnderTest.hasUser(User.withId("non-exist-user-id", "hunter"));
        assertFalse(actual);
    }

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void createUser_withValidInput_thenReturnNewUser() {
        User actual = adapterUnderTest.createUser(User.withId("third-user-id", "new user"));
        assertEquals(User.withId("third-user-id", "new user"), actual);
        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void createUser_withExistedUserId_thenReturnNull() {
        User actual = adapterUnderTest.createUser(User.withId("first-user-id", "new user"));
        assertNull(actual);
        assertEquals(2, userRepository.findAll().size());
    }

}
