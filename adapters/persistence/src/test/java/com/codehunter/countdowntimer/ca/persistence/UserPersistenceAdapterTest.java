package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.core.port.in.user.ICreateUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.user.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.security.ERole;
import com.codehunter.countdowntimer.ca.domain.security.User;
import com.codehunter.countdowntimer.ca.persistence.entity.RoleJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.entity.UserJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.RoleMapper;
import com.codehunter.countdowntimer.ca.persistence.mapper.UserMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.RoleRepository;
import com.codehunter.countdowntimer.ca.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserMapper.class, RoleMapper.class})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserPersistenceAdapterTest {

    @Autowired
    private UserPersistenceAdapter adapterUnderTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Sql(value = "UserPersistenceAdapterTest.sql")
    void getAllRole_thenReturn3Role() {

        List<RoleJpaEntity> actual = roleRepository.findAll();
        assertEquals(3, actual.size());
    }

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    void getAllUser_whenInit_thenReturn1User() {
        List<RoleJpaEntity> roles = roleRepository.findAll();
        assertEquals(3, roles.size());
        UserJpaEntity userJpaEntity = new UserJpaEntity(1L,
                "admin",
                "admin@admin.com",
                "admin",
                Collections.singleton(new RoleJpaEntity(3, ERole.ADMINISTRATOR)));
        List<UserJpaEntity> expected = Arrays.asList(userJpaEntity);
        List<UserJpaEntity> actual = userRepository.findAll();
        assertEquals(1, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = "UserPersistenceAdapterTest.sql")
    void createUser_withValidData_thenReturnNewUser() {
        List<RoleJpaEntity> roles = roleRepository.findAll();
        assertEquals(3, roles.size());
        User expected = new User(new User.UserId(2L), "username", "password", "email@gmail.com", Collections.singleton(ERole.USER));
        User actual = adapterUnderTest.createUser(new ICreateUserUseCase.CreateUserIn("username",
                "email@gmail.com",
                "password"));
        assertEquals(expected, actual);

    }

    @Test
    @Sql(value = "UserPersistenceAdapterTest.sql")
    void hasUser_withExistedName_thenReturnTrue() {
        boolean actual = adapterUnderTest.hasUser(new IHasUserPort.HasUserIn("admin", "lala@gmail.com"));
        assertEquals(true, actual);
    }

    @Test
    @Sql(value = "UserPersistenceAdapterTest.sql")
    void hasUser_withExistedEmail_thenReturnTrue() {
        boolean actual = adapterUnderTest.hasUser(new IHasUserPort.HasUserIn("", "admin@admin.com"));
        assertEquals(true, actual);
    }

    @Test
    @Sql(value = "UserPersistenceAdapterTest.sql")
    void hasUser_withValidInput_thenReturnFalse() {
        boolean actual = adapterUnderTest.hasUser(new IHasUserPort.HasUserIn("newuser", "newuser@admin.com"));
        assertEquals(false, actual);
    }
}
