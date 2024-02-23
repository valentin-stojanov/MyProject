package com.myproject.project.service;

import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.mapper.UserMapper;
import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AdminService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserViewModel> getAllUsers(Pageable pageable) {
        return this.userRepository
                .findAll(pageable)
                .map(this.userMapper::userEntityToUserViewModel);
    }

    public UserEntity lockUser(Long userId){
        return this.userRepository.save(
                this.userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ObjectNotFoundException("User with id: " + userId + " was not found!"))
                        .setAccountNonLocked(false)
                );
    }

    public UserEntity unlockUser(Long userId) {
        return this.userRepository.save(
                this.userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ObjectNotFoundException("User with id: " + userId + " was not found!"))
                        .setAccountNonLocked(true)
        );
    }
}
