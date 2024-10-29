package com.zeero.zeero.controller;

import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.GenericResponse;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UsersController {
    private final UserService userService;

    @PutMapping("/update")
    public GenericResponse<Users> updateUser(@RequestBody UserDetailRequest request) {
        return new GenericResponse<>(userService.updateUserDetail(request));
    }

    @GetMapping("/{id}")
    public GenericResponse<Users> getUser(@PathVariable Long id) {
        return new GenericResponse<>(userService.getUser(id));
    }

    @GetMapping("/all-users")
    public GenericResponse<Page<Users>> allUsers(Pageable pageable) {
        return new GenericResponse<>(userService.getAllUsers(pageable));
    }
}
