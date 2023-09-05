package com.codehunter.countdowntimer.ca.adapter.web.controller.config;

import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.ICreateEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IDeleteEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IGetAllEventWithUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventUseCase;
import com.codehunter.countdowntimer.ca.core.port.in.IUpdateEventWithUserUseCase;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    private static final String AUTHORITY_PREFIX = "ROLE_";
    private static final String CLAIM_ROLES = "roles";
    public static final String AUTH0_TOKEN_ADMIN = "token-admin";
    public static final String AUTH0_TOKEN_USER = "token-user";
    static final String SUB = "sub";
    public static final String USER_AUTH0ID = "a4c161d5-2b54-472b-81b0-2ae558c71dc3";
    public static final String USERNAME_USER = "user";
    public static final String USERNAME_ADMIN = "admin";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/event/**")
                        .hasAnyRole(ROLE_USER, ROLE_ADMIN)
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(getJwtAuthenticationConverter())))
        ;
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }

    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(getJwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> getJwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix(AUTHORITY_PREFIX);
        converter.setAuthoritiesClaimName(CLAIM_ROLES);
        return converter;
    }

    @Bean
    @Primary
    public JwtDecoder jwtDecoder() {
        // This anonymous class needs for the possibility of using SpyBean in test methods
        // Lambda cannot be a spy with spring @SpyBean annotation
        return new JwtDecoder() {
            @Override
            public Jwt decode(String token) {
                if (AUTH0_TOKEN_USER.equals(token)) {
                    return jwtUser();
                }
                return jwtAdmin();
            }
        };
    }

    public Jwt jwtAdmin() {
        // This is a place to add general and maybe custom claims which should be available after parsing token in the live system
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUB, USER_AUTH0ID);
        claims.put("roles", Arrays.asList(ROLE_ADMIN));
        claims.put("preferred_username", USERNAME_ADMIN);
        //This is an object that represents contents of jwt token after parsing
        return new Jwt(
                AUTH0_TOKEN_ADMIN,
                Instant.now(),
                Instant.now().plusSeconds(30),
                Collections.singletonMap("alg", "none"),
                claims
        );
    }

    public Jwt jwtUser() {
        // This is a place to add general and maybe custom claims which should be available after parsing token in the live system
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUB, USER_AUTH0ID);
        claims.put("roles", Arrays.asList(ROLE_USER));
        claims.put("preferred_username", USERNAME_USER);
        //This is an object that represents contents of jwt token after parsing
        return new Jwt(
                AUTH0_TOKEN_USER,
                Instant.now(),
                Instant.now().plusSeconds(30),
                Collections.singletonMap("alg", "none"),
                claims
        );
    }

    @Bean
    @Primary
    public ICreateEventUseCase createEventUseCase() {
        return Mockito.mock(ICreateEventUseCase.class);
    }

    @Bean
    @Primary
    public ICreateEventWithUserUseCase createEventWithUserUseCase() {
        return Mockito.mock(ICreateEventWithUserUseCase.class);
    }

    @Bean
    @Primary
    public IDeleteEventUseCase deleteEventUseCase() {
        return Mockito.mock(IDeleteEventUseCase.class);
    }

    @Bean
    @Primary
    public IDeleteEventWithUserUseCase deleteEventWithUserUseCase() {
        return Mockito.mock(IDeleteEventWithUserUseCase.class);
    }

    @Bean
    @Primary
    public IGetAllEventUseCase getAllEventUseCase() {
        return Mockito.mock(IGetAllEventUseCase.class);
    }

    @Bean
    @Primary
    public IGetAllEventWithUserUseCase getAllEventiwGetAllEventWithUserUseCase() {
        return Mockito.mock(IGetAllEventWithUserUseCase.class);
    }

    @Bean
    @Primary
    public IUpdateEventUseCase updateEventUseCase() {
        return Mockito.mock(IUpdateEventUseCase.class);
    }

    @Bean
    @Primary
    public IUpdateEventWithUserUseCase updateEventWithUserUseCase() {
        return Mockito.mock(IUpdateEventWithUserUseCase.class);
    }

}
