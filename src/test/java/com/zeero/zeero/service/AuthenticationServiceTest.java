package com.zeero.zeero.service;

import com.zeero.zeero.TestUtil;
import com.zeero.zeero.dto.request.LogInRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.LogInResponse;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UserRepository;
import com.zeero.zeero.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private AuthenticationManager authenticationManagerMock;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authService;

    @Test
    void authenticateUser() {
        LogInRequest request = new LogInRequest("joye@gmail.com", "password123");
        UserDetailRequest request1 = TestUtil.getRequest();
        Users users= TestUtil.getUsers(request1);
        String jwt = "jwt_token";

        when(userRepositoryMock.findByEmail(request.getEmail())).thenReturn(Optional.of(users));
        when(passwordEncoderMock.matches(request.getPassword(), users.getPassword())).thenReturn(true);
        when(jwtService.generateToken(users)).thenReturn(jwt);

        LogInResponse response = authService.authenticateUser(request);

        verify(jwtService).generateToken(users);

        assertNotNull(response);
        assertEquals("Log in successful", response.getMessage());
        assertEquals(jwt, response.getToken());
    }

}