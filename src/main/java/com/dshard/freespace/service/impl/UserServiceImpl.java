package com.dshard.freespace.service.impl;

import com.dshard.freespace.model.RequestFormUser;
import com.dshard.freespace.model.Role;
import com.dshard.freespace.model.User;
import com.dshard.freespace.persistance.UserRepository;
import com.dshard.freespace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public User saveUser(RequestFormUser userForm) {
        logger.info("Saved user {}", userForm.getUsername());
        User user = User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .username(userForm.getUsername())
                .password(userForm.getPassword())
                .role(Role.USER)
                .words(0L)
                .blogs(0L)
                .likes(0L)
                .comments(0L)
                .build();
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
                .toList();
    }

}
