package com.codehunter.countdowntimer.ca.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
// Consider to remove in future
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS" );

    }
}
