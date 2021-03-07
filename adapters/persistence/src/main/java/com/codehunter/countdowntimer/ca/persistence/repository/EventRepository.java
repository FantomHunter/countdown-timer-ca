package com.codehunter.countdowntimer.ca.persistence.repository;

import com.codehunter.countdowntimer.ca.persistence.entity.EventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventJpaEntity, Long> {
    @Query("select e from EventJpaEntity e where e.user.id = :userId")
    List<EventJpaEntity> findByUser(@Param("userId") String userId);
}
