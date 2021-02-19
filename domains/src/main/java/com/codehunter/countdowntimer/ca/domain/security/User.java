package com.codehunter.countdowntimer.ca.domain.security;

import lombok.Value;

import java.util.Set;

@Value
public class User {
    private UserId id;
    private String name;
    private String password;
    private String email;
    private Set<ERole> roles;

    @Value
    public static class UserId {
        Long value;
    }
}
