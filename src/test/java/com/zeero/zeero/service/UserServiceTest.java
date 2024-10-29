package com.zeero.zeero.service;

import com.zeero.zeero.TestUtil;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.exceptions.TodoAppException;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void updateUserDetail() {
        UserDetailRequest request = TestUtil.getRequest();
        Users expected = TestUtil.getUsers(request);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(expected);
        SecurityContextHolder.setContext(securityContext);

        when(userRepositoryMock.save(any(Users.class))).thenReturn(expected);

       Users result = userService.updateUserDetail(request);

        assertNotNull(result);
        assertEquals(expected, result);

        verify(userRepositoryMock).save(any(Users.class));
        verify(securityContext).getAuthentication();
        verify(authentication).getPrincipal();
    }

    @Test
    void getAllUsers() {
        UserDetailRequest request = TestUtil.getRequest();
        Users expected = TestUtil.getUsers(request);
        expected.setId(1L);
        Page<Users> expectedPatronPage = mock(Page.class);

        when(userRepositoryMock.findAll(pageable)).thenReturn(expectedPatronPage);

        Page<Users> result = userService.getAllUsers(pageable);

        assertNotNull(result);

        verify(userRepositoryMock).findAll(pageable);
    }

    @Test
    void getUser() {
        UserDetailRequest request = TestUtil.getRequest();
        Users expected = TestUtil.getUsers(request);
        expected.setId(1L);

        when(userRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

       Users result = userService.getUser(expected.getId());

        assertNotNull(result);
        assertEquals(expected, result);

        verify(userRepositoryMock).findById(any(Long.class));
    }

}