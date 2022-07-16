package com.myproject.project.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String email;
    private final String fullName;

    private final Collection<GrantedAuthority> authorities;

    public AppUserDetails(String username,
                          String password,
                          String email,
                          String fullName,
                          Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
