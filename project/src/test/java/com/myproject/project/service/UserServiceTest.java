package com.myproject.project.service;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.enums.RoleEnum;
import com.myproject.project.model.mapper.UserMapper;
import com.myproject.project.model.user.AppUserDetails;
import com.myproject.project.repository.UserRepository;
import com.myproject.project.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserDetailsService userDetailsServiceMock;
    @Mock
    private UserMapper userMapperMock;
    private UserService toTest;

    private UserEntity testUserEntity;

    @BeforeEach
    void setUp() {
        toTest = new UserService(passwordEncoderMock,
                userRepositoryMock,
                userDetailsServiceMock,
                userMapperMock);

        testUserEntity = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("topsecret")
                .setFirstName("Test")
                .setLastName("Testov")
                .setAge(18)
                .setRoles(List.of(
                                new RoleEntity().setRole(RoleEnum.ADMIN),
                                new RoleEntity().setRole(RoleEnum.USER)
                        )
                );
    }

    @Test
    void canRegisterUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto()
                .setFirstName("Test")
                .setLastName("Testov")
                .setAge(18)
                .setEmail("test@mail.com")
                .setPassword("topsecret")
                .setConfirmPassword("topsecret");

        when(userMapperMock.toUserEntity(userRegistrationDto))
                .thenReturn(new UserEntity()
                        .setAge(userRegistrationDto.getAge())
                        .setEmail(userRegistrationDto.getEmail())
                        .setFirstName(userRegistrationDto.getFirstName())
                        .setLastName(userRegistrationDto.getLastName())
                        .setPassword(userRegistrationDto.getPassword()));

        toTest.registerUser(userRegistrationDto);
        verify(userMapperMock).toUserEntity(userRegistrationDto);
//        verify(passwordEncoderMock).encode(userRegistrationDto.getPassword());
        verify(userRepositoryMock).save(any(UserEntity.class));

    }

    @Test
    void shouldFindUserByEmail() {

        when(userRepositoryMock.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        toTest.findUserByEmail(testUserEntity.getEmail());

        verify(userRepositoryMock).findByEmail(same(testUserEntity.getEmail()));
    }

    @Test
    void userNotFoundByEmail() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> toTest
                        .findUserByEmail("not-existant@email.com"));
    }

    @Test
    void shouldGetUserInfo() {
        when(userRepositoryMock.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        UserViewModel userInfo = toTest.getUserInfo(testUserEntity.getEmail());

        Assertions.assertEquals(testUserEntity.getFirstName() + " " + testUserEntity.getLastName(),
                userInfo.getFullName());
        Assertions.assertEquals(testUserEntity.getAge(), userInfo.getAge());
        Assertions.assertEquals(testUserEntity.getEmail(), userInfo.getEmail());
    }

    @Test
    void shouldNotGetUserInfo() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> toTest.getUserInfo(testUserEntity.getEmail()));
    }
}