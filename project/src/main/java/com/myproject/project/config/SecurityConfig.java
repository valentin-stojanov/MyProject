package com.myproject.project.config;

import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                // define which requests are allowed and which not
                        authorizeHttpRequests(authorizeHttpRequests ->
                                authorizeHttpRequests.
                                        // everyone can download static resources (css, js, images)
                                                requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                                        // everyone can log in and register
                                                requestMatchers("/",
//                        "/login/oauth2/code/google",
                                                "/users/login",
                                                "/users/register",
                                                "/users/reset-password",
                                                "/users/reset-password/reset/**",
                                                "/route").permitAll().
                                        // all other pages are available for logger in users
                                                anyRequest().
                                        authenticated()
                ).
                // configuration of form login
                        formLogin((formLogin) ->

                        formLogin.
                                // the custom login form
                                        loginPage("/users/login").
                                // the name of the username form field
                                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                                // the name of the password form field
                                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                                // where to go in case that the login is successful
                                        defaultSuccessUrl("/").
                                // where to go in case that the login failed
                                        failureForwardUrl("/users/login-error")
                ).
                // configure logout
                        logout((logout) ->
                        logout.
                                // which is the logout url, must be POST request
                                        logoutUrl("/users/logout").
                                // on logout go to the home page
                                        logoutSuccessUrl("/").
                                // invalidate the session and delete the cookies
                                        invalidateHttpSession(true)
                )
                .oauth2Login((oauth2Login) -> oauth2Login
                        .userInfoEndpoint((userInfo) -> userInfo
                                .userAuthoritiesMapper(grantedAuthoritiesMapper())
                        )
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }

    private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach((authority) -> {
                GrantedAuthority mappedAuthority;

                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
                    mappedAuthority = new OidcUserAuthority(
                            "OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
                    mappedAuthority = new OAuth2UserAuthority(
                            "OAUTH2_USER", userAuthority.getAttributes());
                } else {
                    mappedAuthority = authority;
                }

                mappedAuthorities.add(mappedAuthority);
            });

            return mappedAuthorities;
        };
    }
}