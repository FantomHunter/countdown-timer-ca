package com.codehunter.countdowntimer.ca.persistence.repository;

import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventJpaEntity, Long> {
}
