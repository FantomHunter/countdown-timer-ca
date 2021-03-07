package com.codehunter.countdowntimer.ca.persistence;


import com.codehunter.countdowntimer.ca.core.port.out.ICreateUserPort;
import com.codehunter.countdowntimer.ca.core.port.out.IHasUserPort;
import com.codehunter.countdowntimer.ca.domain.User;
import com.codehunter.countdowntimer.ca.persistence.entity.UserJpaEntity;
import com.codehunter.countdowntimer.ca.persistence.mapper.UserMapper;
import com.codehunter.countdowntimer.ca.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@PersistenceAdapter
@Slf4j
public class UserPersistenceAdapter implements IHasUserPort, ICreateUserPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean hasUser(User user) {
        log.info("has user: {}", user);
        return userRepository.existsById(user.getId().getValue());
    }


    @Override
    public User createUser(User user) {
        log.info("create user: {}", user);

        UserJpaEntity userJpaEntity = userMapper.mapToJpaEntity(user);
        if (!userRepository.existsById(user.getId().getValue())) {
            UserJpaEntity out = userRepository.save(userJpaEntity);
            return userMapper.mapToUser(out);
        }
        return null;
    }
}
