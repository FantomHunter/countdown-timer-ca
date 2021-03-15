package com.codehunter.countdowntimer.ca.domain;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class User {
    @Getter
    private final UserId id;
    @Getter
    private final String name;

    public static User withId(String id, String name) {
        return new User(new UserId(id), name);
    }

    @Value
    public static class UserId {
        String value;
    }
}
