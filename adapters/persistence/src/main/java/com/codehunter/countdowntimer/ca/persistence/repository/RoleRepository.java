package com.codehunter.countdowntimer.ca.persistence.repository;

import com.codehunter.countdowntimer.ca.domain.security.ERole;
import com.codehunter.countdowntimer.ca.persistence.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
    Optional<RoleJpaEntity> findByName(ERole name);
}
