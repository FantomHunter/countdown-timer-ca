package com.codehunter.countdowntimer.ca.adapter.web.controller.util;

import com.codehunter.countdowntimer.ca.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class OauthUserUtil {
    public boolean hasAnyRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> listAuth = auth.getAuthorities();
        return listAuth.stream().anyMatch(r -> role.equalsIgnoreCase(r.getAuthority()));
    }

    public Optional<User> getUserFromJwtPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt) {

            Jwt principal = (Jwt) auth.getPrincipal();
            String userId = principal.getClaimAsString("sub");
            String username = principal.getClaimAsString("preferred_username");
            return Optional.of(User.withId(userId, username));
        }
        return Optional.empty();
    }

}
