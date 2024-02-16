package com.myproject.project.service;

import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.model.mapper.UserMapper;
import com.myproject.project.repository.UserRepository;
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
}
