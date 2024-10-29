package com.zeero.zeero.service;


import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.exceptions.ErrorStatus;
import com.zeero.zeero.exceptions.TodoAppException;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService {
    private final UserRepository userRepository;

    public Users updateUserDetail(UserDetailRequest request) {
        Users users = loggedInUser();
        updateFieldIfNotNullOrEmpty(request.getFirstName(), users::setFirstName);
        updateFieldIfNotNullOrEmpty(request.getLastName(), users::setLastName);
        updateFieldIfNotNullOrEmpty(request.getEmail(), users::setEmail);
        return userRepository.save(users);
    }


    @Cacheable(value = "userCache")
    public Page<Users> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Cacheable(value = "userCache", key = "#id")
    public Users getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new TodoAppException(ErrorStatus.USER_NOT_FOUND_ERROR, "User not found"));
    }


}
