package com.dev.cinema.service;

import com.dev.cinema.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    User add(User user);
}
