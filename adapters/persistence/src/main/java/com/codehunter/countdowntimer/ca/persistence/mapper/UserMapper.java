package com.codehunter.countdowntimer.ca.persistence.mapper;

import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {

    public UserJpaEntity mapToJpaEntity(User user) {
        return new UserJpaEntity(user.getId().getValue(), user.getName(), Collections.emptyList());
    }

    public User mapToUser(UserJpaEntity userJpaEntity) {
        return User.withId(userJpaEntity.getId(), userJpaEntity.getName());
    }
}
