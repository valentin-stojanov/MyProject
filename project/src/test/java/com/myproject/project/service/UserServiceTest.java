package com.myproject.project.service;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.entity.PasswordResetTokenEntity;
import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.enums.RoleEnum;
import com.myproject.project.model.mapper.UserMapper;
import com.myproject.project.repository.PasswordResetTokenRepository;
import com.myproject.project.repository.RoleRepository;
import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepositoryMock;
    @Mock
    private UserDetailsService userDetailsServiceMock;
    @Mock
    private UserMapper userMapperMock;
    @Mock
    private EmailService emailServiceMock;
    @Mock
    private RoleRepository roleRepositoryMock;

    @InjectMocks
    private UserService toTest;
    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        toTest = new UserService(passwordEncoderMock,
                userRepositoryMock,
                userDetailsServiceMock,
                userMapperMock,
                emailServiceMock,
                passwordResetTokenRepositoryMock,
                roleRepositoryMock);

        testUserEntity = new UserEntity()
                .setFirstName("Test")
                .setLastName("Testov")
                .setAge(18)
                .setEmail("test@mail.com")
                .setPassword("topsecret");
    }

    @Test()
    @DisplayName("Should register user with correct data.")
    void register_Success() {
        RoleEntity role = new RoleEntity().setRole(RoleEnum.USER);

        when(roleRepositoryMock.findByRole(RoleEnum.USER))
                .thenReturn(Optional.of(role));
        when(userRepositoryMock.save(testUserEntity)).thenReturn(testUserEntity);

//        Act
        String registered = toTest.register(testUserEntity);

        assertThat(testUserEntity.getRoles().get(0)).isEqualTo(role);
        assertThat(registered).isEqualTo(testUserEntity.getEmail());
        verify(roleRepositoryMock).findByRole(RoleEnum.USER);
        verify(userRepositoryMock).save(testUserEntity);
        verify(emailServiceMock).sendRegistrationEmail(testUserEntity.getEmail(), testUserEntity.getFirstName());
    }

    @Test
    @DisplayName("Should not register user with role different from USER.")
    void register_Fail() {
        when(roleRepositoryMock.findByRole(RoleEnum.USER))
                .thenReturn(Optional.empty());
        //        Act
        IllegalStateException thrownException = Assertions.assertThrows(IllegalStateException.class,
                () -> toTest.register(testUserEntity));

        assertThat(thrownException.getMessage())
                .isEqualTo("Invalid Role");
    }

    @Test
    @DisplayName("Check for correct invocation of of register().")
    void registerUser_Success() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto()
                .setFirstName("Test")
                .setLastName("Testov")
                .setAge(18)
                .setEmail("test@mail.com")
                .setPassword("topsecret")
                .setConfirmPassword("topsecret");
        UserEntity newUser = testUserEntity;
        RoleEntity role = new RoleEntity();

        when(userMapperMock.userRegistrationDtoToUserEntity(userRegistrationDto)).thenReturn(newUser);
        when(roleRepositoryMock.findByRole(RoleEnum.USER)).thenReturn(Optional.of(role));
        when(userRepositoryMock.save(newUser)).thenReturn(newUser);

//        Act
        String registeredEmail = toTest.registerUser(userRegistrationDto);

        verify(userMapperMock).userRegistrationDtoToUserEntity(userRegistrationDto);

        // Indirect verification for interactions with the --> package private String register(UserEntity e)
        verify(roleRepositoryMock).findByRole(RoleEnum.USER);
        verify(userRepositoryMock).save(newUser);
        verify(emailServiceMock).sendRegistrationEmail(newUser.getEmail(), newUser.getFirstName());

        // Verification for return value of registerUser()
        assertThat(registeredEmail).isEqualTo(userRegistrationDto.getEmail());
    }

    @Test
    @DisplayName("Throw an error for non existent user email.")
    void findUserByEmail_Fail() {
        String invalidEmail = "not-existent@email.com";

//        Act
        ObjectNotFoundException objectNotFoundException = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> toTest
                        .findUserByEmail(invalidEmail));
        assertThat(objectNotFoundException.getMessage())
                .isEqualTo("Email: " + invalidEmail + " was not found!");
    }

    @Test
    @DisplayName("Find user by existent user email.")
    void findUserByEmail_Success() {
        String validEmail = testUserEntity.getEmail();
        when(userRepositoryMock.findByEmail(validEmail))
                .thenReturn(Optional.of(testUserEntity));

//        Act
        toTest.findUserByEmail(validEmail);

        verify(userRepositoryMock).findByEmail(validEmail);
    }

    @Test
    @DisplayName("Should get information about given user.")
    void getUserInfo_Success() {
        String validEmail = testUserEntity.getEmail();
        when(userRepositoryMock.findByEmail(validEmail))
                .thenReturn(Optional.of(testUserEntity));
//        Act
        toTest.getUserInfo(validEmail);

        verify(userRepositoryMock).findByEmail(validEmail);
    }

    @Test
    @DisplayName("Should throw an error for non existent email")
    void getUserInfo_Fail() {
        String invalidEmail = "not-existent@email.com";
        when(userRepositoryMock.findByEmail(invalidEmail))
                .thenReturn(Optional.empty());

//        Act
        ObjectNotFoundException objectNotFoundException = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> toTest.getUserInfo(invalidEmail));

        assertThat(objectNotFoundException.getMessage())
                .isEqualTo("User with email: " + invalidEmail + " was not found!");
    }

    @Test
    @DisplayName("Should generate password reset token for user")
    void generatePasswordResetTokenForUser_Success() {
        String userEmail = testUserEntity.getEmail();

        when(userRepositoryMock.findByEmail(userEmail))
                .thenReturn(Optional.of(testUserEntity));
        when(passwordResetTokenRepositoryMock.save(any(PasswordResetTokenEntity.class)))
                .then(invocation -> invocation.getArgument(0));
        when(userRepositoryMock.save(any(UserEntity.class)))
                .then(invocation -> invocation.getArgument(0));
//        Act
        UserEntity updatedUser = toTest.generatePasswordResetTokenForUser(userEmail);

        Assertions.assertNotNull(updatedUser.getPasswordResetToken());
        verify(passwordResetTokenRepositoryMock)
                .save(any(PasswordResetTokenEntity.class));
        Assertions.assertNotNull(updatedUser.getPasswordResetToken().getResetToken());
        Assertions.assertNotNull(updatedUser.getPasswordResetToken().getCreated());
        verify(userRepositoryMock).save(updatedUser);
    }

}