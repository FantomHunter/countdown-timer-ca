package com.codehunter.countdowntimer.ca.persistence;

import com.codehunter.countdowntimer.ca.core.port.in.user.ICreateUserUseCase;
import com.codehunter.countdowntimer.ca.core.port.out.user.ICreateUserPort;
import com.codehunter.countdowntimer.ca.core.port.out.user.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.security.User;
import com.codehunter.countdowntimer.ca.persistence.entity.UserJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.UserMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PersistenceAdapter
@RequiredArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements ICreateUserPort, IHasUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(ICreateUserUseCase.CreateUserIn createUserIn) {
        log.info("create user from {}", createUserIn);
        UserJpaEntity userJpaEntity = userRepository.save(userMapper.mapToJpaEntity(createUserIn));
        return userMapper.mapToUser(userJpaEntity);
    }

    @Override
    public boolean hasUser(HasUserIn hasUserIn) {
        log.info("check hasUser {}", hasUserIn);
        if (!userRepository.existsByEmail(hasUserIn.getEmail()) && !userRepository.existsByUsername(hasUserIn.getName())) {
            return false;
        }
        return true;
    }
}
