package com.codehunter.countdowntimer.ca.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "time")
    private Instant publicTime;
    @Column(columnDefinition = "varchar(25) default 'CREATED'")
    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.CREATED;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

}
