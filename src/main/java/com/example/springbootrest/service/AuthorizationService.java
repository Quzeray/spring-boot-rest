package com.example.springbootrest.service;

import com.example.springbootrest.model.Authorities;
import com.example.springbootrest.repository.UserRepository;
import com.example.springbootrest.service.exception.IncorrectPasswordException;
import com.example.springbootrest.service.exception.InvalidCredentials;
import com.example.springbootrest.service.exception.UnauthorizedUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationService {
    final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Authorities> getAuthorities(String user, String password)
            throws InvalidCredentials, UnauthorizedUser, IncorrectPasswordException {
        if (isEmpty(user) || isEmpty(password)) {
            throw new InvalidCredentials("User name or password is empty");
        }

        List<Authorities> userAuthorities = userRepository.getUserAuthorities(user, password);
        if (isEmpty(userAuthorities)) {
            throw new UnauthorizedUser("Unknown user " + user);
        }
        return userAuthorities;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(List<?> str) {
        return str == null || str.isEmpty();
    }
}