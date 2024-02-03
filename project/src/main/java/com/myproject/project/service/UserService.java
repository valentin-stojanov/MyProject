package com.myproject.project.service;

import com.myproject.project.model.dto.UserProfileEditDto;
import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.dto.UserResetPasswordDto;
import com.myproject.project.model.entity.PasswordResetTokenEntity;
import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.enums.RoleEnum;
import com.myproject.project.model.mapper.UserMapper;
import com.myproject.project.repository.PasswordResetTokenRepository;
import com.myproject.project.repository.RoleRepository;
import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final String OAUTH2_DEFAULT_PASSWORD = "OAuth2_authentication";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RoleRepository roleRepository;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       UserDetailsService userDetailsService,
                       UserMapper userMapper, EmailService emailService,
                       PasswordResetTokenRepository passwordResetTokenRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.roleRepository = roleRepository;
    }

    public String registerUserIfNotExist(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        String userEmail = oAuth2AuthenticationToken
                .getPrincipal()
                .getAttribute("email");

        Optional<UserEntity> userOpt = this.userRepository.findByEmail(userEmail);

        if (userOpt.isEmpty()) {
            UserEntity newUser = createUserEntityFromOAuth2AuthenticationToken(oAuth2AuthenticationToken);
            return register(newUser);
        }
        return userOpt.get().getEmail();
    }

    public String registerUser(UserRegistrationDto userRegistrationDto) {
        UserEntity newUser = this.userMapper.userRegistrationDtoToUserEntity(userRegistrationDto);
        return register(newUser);
    }

    String register(UserEntity newUser) {
        Optional<RoleEntity> optRole = this.roleRepository.findByRole(RoleEnum.USER);
        if(optRole.isEmpty()){
            throw new IllegalStateException("Invalid Role");
        }
        RoleEntity role = optRole.get();

        newUser.setRoles(List.of(role));
        UserEntity registeredUser = this.userRepository.save(newUser);
        this.emailService.sendRegistrationEmail(newUser.getEmail(), newUser.getFirstName());
        return registeredUser.getEmail();
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

    public UserEntity getUserInfo(String username) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByEmail(username);

        if (optionalUserEntity.isEmpty()) {
            throw new ObjectNotFoundException("User with email: " + username + " was not found!");
        }

        return optionalUserEntity.get();
    }

    private UserEntity createUserEntityFromOAuth2AuthenticationToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {

        String clientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = oAuth2AuthenticationToken
                .getPrincipal()
                .getAttributes();
        UserEntity userEntity = new UserEntity()
                .setPassword(OAUTH2_DEFAULT_PASSWORD);

        switch (clientRegistrationId) {
            case "google":
                userEntity
                        .setEmail(attributes.get("email").toString())
                        .setFirstName(attributes.get("given_name").toString())
                        .setLastName(attributes.get("family_name").toString());
                break;
            case "github":
                String name = attributes.get("name").toString();
                String[] names = name.split("\\s+");
                userEntity
                        .setEmail(attributes.get("email").toString())
                        .setFirstName(names[0])
                        .setLastName(names.length > 1 ? names[1] : "");
                break;
            default:
                throw new IllegalStateException("Invalid client registration ID " + clientRegistrationId);
        }

        return userEntity;
    }

    public UserEntity generatePasswordResetTokenForUser(String email) {
        UserEntity user = this.userRepository
                .findByEmail(email)
                .orElseThrow();

        PasswordResetTokenEntity passwordResetToken = new PasswordResetTokenEntity()
                .setResetToken(UUID.randomUUID().toString())
                .setCreated(LocalDateTime.now())
                .setUser(user);

        PasswordResetTokenEntity userResetToken = this.passwordResetTokenRepository.save(passwordResetToken);

        user.setPasswordResetToken(userResetToken);

        return this.userRepository.save(user);
    }

    public String generateResetUrl(String email) {

        UserEntity user = generatePasswordResetTokenForUser(email);
        String resetToken = user.getPasswordResetToken().getResetToken();

        return "http://localhost:8080/users/reset-password/reset/" + resetToken;
    }

    public void resetPasswordWithResetToken(String token, UserResetPasswordDto userResetPasswordDto) {
        LocalDateTime currentTime = LocalDateTime.now();

        Optional<UserEntity> optionalUserEntity = this.userRepository.findByPasswordResetToken(token);

        if (optionalUserEntity.isEmpty()) {
            throw new IllegalStateException("Invalid token");
        }

        UserEntity user = optionalUserEntity.get();
        user.setPassword(this.passwordEncoder.encode(userResetPasswordDto.getPassword()));
        this.userRepository.save(user);

    }


    public void checkTypeOfRegistration(String email) {
        int oAuth2_authentication = this.userRepository
                .findByEmail(email)
                .get()
                .getPassword()
                .compareTo(OAUTH2_DEFAULT_PASSWORD);
        if (oAuth2_authentication == 0){
            throw new IllegalStateException("Password reset is not applicable");
        }
    }

    public void updateUserInfo(String username, UserProfileEditDto userProfileEditDto) {
        Optional<UserEntity> optionalUser = this.userRepository
                .findByEmail(username);

        if (optionalUser.isEmpty()){
            throw new IllegalStateException("Empty user");
        }

        UserEntity user = optionalUser.get().setFirstName(userProfileEditDto.getFirstName())
                .setLastName(userProfileEditDto.getLastName())
                .setAge(userProfileEditDto.getAge());
        this.userRepository.save(user);

    }
}
