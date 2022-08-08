package com.myproject.project.service;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    public UserEntity registerUser(UserRegistrationDto userRegistrationDto) {
        UserEntity newUser = new UserEntity()
                .setUsername(userRegistrationDto.getUsername())
                .setFirstName(userRegistrationDto.getFirstName())
                .setLastName(userRegistrationDto.getLastName())
                .setEmail(userRegistrationDto.getEmail())
                .setPassword(this.passwordEncoder.encode(userRegistrationDto.getPassword()))
                .setAge(userRegistrationDto.getAge());

        return this.userRepository.save(newUser);
    }

    public void login(UserEntity userEntity) {
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(userEntity.getEmail());

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }
}
