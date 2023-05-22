package com.example.springbootrest.repository;

import com.example.springbootrest.model.Authorities;
import com.example.springbootrest.model.User;
import com.example.springbootrest.service.exception.IncorrectPasswordException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.springbootrest.model.Authorities.*;


@Repository
public class UserRepository {
    private final static List<User> users = Arrays.asList(
            new User("read", "read", List.of(READ)),
            new User("write", "write", List.of(WRITE)),
            new User("read_write", "read_write", Arrays.asList(READ, WRITE)),
            new User("read_write_delete", "read_write_delete", Arrays.asList(READ, WRITE, DELETE))
    );

    public List<Authorities> getUserAuthorities(String name, String password) throws IncorrectPasswordException {
        Optional<User> optionalUser = users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }

        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return user.getPassword().equals(password) ? user.getAuthorities() : new ArrayList<>();
    }
}