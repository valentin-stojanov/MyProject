package com.myproject.project.service;

import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.user.AppUserDetails;
import com.myproject.project.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(email)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email));
    }

    private UserDetails map(UserEntity userEntity) {
        return new AppUserDetails(userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity
                        .getRoles()
                        .stream()
                        .map(this::map)
                        .toList());
    }

    private GrantedAuthority map(RoleEntity userRole){
        return new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name());
    }
}
