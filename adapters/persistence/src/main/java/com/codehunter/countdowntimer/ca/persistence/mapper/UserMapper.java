package com.codehunter.countdowntimer.ca.persistence.mapper;

import com.codehunter.countdowntimer.ca.core.port.in.user.ICreateUserUseCase;
import com.codehunter.countdowntimer.ca.domain.security.ERole;
import com.codehunter.countdowntimer.ca.domain.security.User;
import com.codehunter.countdowntimer.ca.persistence.entity.RoleJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.entity.UserJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserMapper {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public UserJpaEntity mapToJpaEntity(ICreateUserUseCase.CreateUserIn createUserIn) {
        RoleJpaEntity userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new EntityNotFoundException(" Role USER not found"));
        return new UserJpaEntity(null,
                createUserIn.getUsername(),
                createUserIn.getEmail(),
                createUserIn.getPassword(),
                Collections.singleton(userRole));
    }

    public User mapToUser(UserJpaEntity userJpaEntity) {
        return new User(new User.UserId(userJpaEntity.getId()),
                userJpaEntity.getUsername(),
                userJpaEntity.getPassword(),
                userJpaEntity.getEmail(),
                roleMapper.mapToSetERole(userJpaEntity.getRoles()));
    }
}
