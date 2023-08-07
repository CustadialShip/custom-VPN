package com.dshard.freespace.service;

import com.dshard.freespace.model.RequestFormUser;
import com.dshard.freespace.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(RequestFormUser userForm);
    Optional<User> getUser(String userId);
    List<User> getUsers(int limit);
}
