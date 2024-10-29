package com.zeero.zeero.controller;


import com.zeero.zeero.dto.request.LogInRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.LogInResponse;
import com.zeero.zeero.dto.response.GenericResponse;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public GenericResponse<Users> createUser(@RequestBody UserDetailRequest request) {
       Users users = authenticationService.createUser(request);
        return new GenericResponse<>(users);
    }

    @PostMapping("/login")
    public GenericResponse<LogInResponse> signIn(@RequestBody LogInRequest request) {
        LogInResponse response = authenticationService.authenticateUser(request);
        return new GenericResponse<>(response);
    }
}
