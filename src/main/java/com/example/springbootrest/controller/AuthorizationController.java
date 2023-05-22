package com.example.springbootrest.controller;

import com.example.springbootrest.model.Authorities;
import com.example.springbootrest.service.AuthorizationService;
import com.example.springbootrest.service.exception.IncorrectPasswordException;
import com.example.springbootrest.service.exception.InvalidCredentials;
import com.example.springbootrest.service.exception.UnauthorizedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
public class AuthorizationController {
    final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @GetMapping("/authorize")
    public List<Authorities> getAuthorities(@RequestParam("user") String user, @RequestParam("password") String password) {
        return service.getAuthorities(user, password);
    }

    @ExceptionHandler(InvalidCredentials.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentials e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedUser.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<String> handleUnauthorizedUser(UnauthorizedUser e) {
        return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<String> handleIncorrectPassword(IncorrectPasswordException e) {
        return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
    }

}