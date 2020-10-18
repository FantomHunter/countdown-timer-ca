package com.codehunter.countdowntimer.ca.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "time")
    private Date publicTime;
    @Column(columnDefinition = "varchar(25) default 'CREATED'")
    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.CREATED;

}
