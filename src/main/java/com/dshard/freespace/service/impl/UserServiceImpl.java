package com.dshard.freespace.service.impl;

import com.dshard.freespace.model.User;
import com.dshard.freespace.persistance.UserRepository;
import com.dshard.freespace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        logger.info("Saved user {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String userId) {
        logger.info("Get user with id {}", userId);
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getUsers(int limit) {
        logger.info("Get users with limit {}", limit);
        return userRepository.findAll().stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

}
