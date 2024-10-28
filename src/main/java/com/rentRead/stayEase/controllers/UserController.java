package com.crio.stayEase.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.stayEase.exchanges.LoginUserRequest;
import com.crio.stayEase.exchanges.LoginUserResponse;
import com.crio.stayEase.exchanges.RegisterUserRequest;
import com.crio.stayEase.exchanges.RegisterUserResponse;
import com.crio.stayEase.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(UserController.USER_API_ENDPOINT)
@RequiredArgsConstructor
public class UserController {
    
    public static final String USER_API_ENDPOINT = "/users";

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        RegisterUserResponse registerUserResponse = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok().body(registerUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        LoginUserResponse LoginUserResponse = userService.loginUser(loginUserRequest);
        return ResponseEntity.ok().body(LoginUserResponse);
    }
}
