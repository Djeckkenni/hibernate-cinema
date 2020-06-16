package com.dev.cinema.service;

import com.dev.cinema.model.User;

public interface UserService {
    User findByEmail(String email);

    User add(User user);

    User getById(Long userId);
}
