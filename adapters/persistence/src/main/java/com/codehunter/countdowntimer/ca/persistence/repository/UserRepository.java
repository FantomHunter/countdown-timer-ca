package com.codehunter.countdowntimer.ca.persistence.repository;

import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJpaEntity, String > {
}
