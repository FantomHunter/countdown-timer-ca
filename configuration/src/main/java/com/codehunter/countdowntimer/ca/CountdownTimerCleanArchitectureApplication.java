package com.codehunter.countdowntimer.ca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CountdownTimerCleanArchitectureApplication {
    public static void main(String[] args) {
        SpringApplication.run(CountdownTimerCleanArchitectureApplication.class, args);
    }

//    @Autowired
//    EventRepository eventRepository;
//    @Autowired
//    EventMapper eventMapper;
//    @Bean
//    public CreateEventPort createEventPort(){
//        return new EventPersistenceAdapter(eventRepository, eventMapper);
//    }

    
}
