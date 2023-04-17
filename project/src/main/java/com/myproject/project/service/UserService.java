package com.myproject.project.service;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.mapper.UserMapper;
import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserDetailsService userDetailsService,
                       UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    public void createUserIfNotExist(String email){
        Optional<UserEntity> userOpt = this.userRepository.findByEmail(email);

        if (userOpt.isEmpty()){
            UserEntity newUser = new UserEntity()
                    .setEmail(email)
                    .setPassword(null)
                    .setFirstName("New")
                    .setLastName("User")
                    .setPassword("OAuth2_authentication")
                    .setRoles(List.of());

            this.userRepository.save(newUser);
        }
    }

    public UserEntity registerUser(UserRegistrationDto userRegistrationDto) {
        UserEntity newUser = this.userMapper.toUserEntity(userRegistrationDto);
        return this.userRepository.save(newUser);
    }

    public void login(String username) {
        UserDetails userDetails = this.userDetailsService
                .loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }

    public UserEntity findUserByEmail(String email) {

        return this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Email: " + email + " was not found!"));
    }

    public UserViewModel getUserInfo(String username) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByEmail(username);

        if (optionalUserEntity.isEmpty()) {
            throw new ObjectNotFoundException("User with email: " + username + " was not found!");
        }

        UserEntity user = optionalUserEntity.get();

        UserViewModel userViewModel = new UserViewModel()
                .setFullName(user.getFirstName() + " " + user.getLastName())
                .setAge(user.getAge())
                .setEmail(user.getEmail());
        return userViewModel;
    }
}
