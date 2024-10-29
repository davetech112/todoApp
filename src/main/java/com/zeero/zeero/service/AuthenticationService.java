package com.zeero.zeero.service;


import com.zeero.zeero.dto.request.LogInRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.LogInResponse;
import com.zeero.zeero.enums.Role;
import com.zeero.zeero.exceptions.ErrorStatus;
import com.zeero.zeero.exceptions.TodoAppException;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UserRepository;
import com.zeero.zeero.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public Users createUser(UserDetailRequest request) {
        Optional<Users> person = userRepository.findByEmail(request.getEmail());
        if (person.isPresent()) {
            log.info("===={}", person);
            throw new TodoAppException(ErrorStatus.USER_ALREADY_EXIST, ErrorStatus.USER_ALREADY_EXIST.getErrorMessage());
        }
        Users user = getUser(request);
        user.setRole(Role.USER);
        userRepository.save(user);

        user.setCreatedBy(user.getFirstName() + " " + user.getLastName());
        userRepository.save(user);

        return user;
    }

    public LogInResponse authenticateUser(LogInRequest request) {
        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new TodoAppException(ErrorStatus.USER_NOT_FOUND_ERROR, ErrorStatus.USER_NOT_FOUND_ERROR.getErrorMessage()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new TodoAppException(ErrorStatus.VALIDATION_ERROR, "Incorrect password");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwt = jwtService.generateToken(user);

        LogInResponse response = new LogInResponse();
        response.setMessage("Log in successful");
        response.setToken(jwt);

        return response;
    }


    private Users getUser(UserDetailRequest request) {
        Users user = new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }
}
