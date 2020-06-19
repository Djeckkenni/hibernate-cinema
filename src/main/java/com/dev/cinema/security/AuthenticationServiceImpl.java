package com.dev.cinema.security;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.UserService;
import java.util.Set;

import com.dev.cinema.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final HashUtil hashUtil;
    private final UserService userService;

    public AuthenticationServiceImpl(HashUtil hashUtil, UserService userService) {
        this.hashUtil = hashUtil;
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user != null
                && hashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Incorrect email or password!!!");
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setSalt(hashUtil.getSalt());
        user.setPassword(hashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}
