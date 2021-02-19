package com.codehunter.countdowntimer.ca.persistence.mapper;

import com.codehunter.countdowntimer.ca.domain.security.ERole;
import com.codehunter.countdowntimer.ca.persistence.entity.RoleJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public ERole mapToERole(RoleJpaEntity roleJpaEntity) {
        return roleJpaEntity.getName();
    }

    public Set<ERole> mapToSetERole(Set<RoleJpaEntity> roleJpaEntitySet) {
        return roleJpaEntitySet.stream().map(roleJpaEntity -> roleJpaEntity.getName()).collect(Collectors.toSet());
    }
}
