package com.myproject.project.service;


import com.myproject.project.model.entity.RoleEntity;
import com.myproject.project.model.entity.UserEntity;
import com.myproject.project.model.enums.RoleEnum;
import com.myproject.project.model.user.AppUserDetails;
import com.myproject.project.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserDetailsServiceTest {
    @Mock
    private UserRepository mockUserRepository;

    private AppUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        //arrange
        UserEntity testUserEntity = new UserEntity()
                .setEmail("test@example.com")
                .setPassword("topsecret")
                .setFirstName("Test")
                .setLastName("Testov")
                .setRoles(List.of(
                                new RoleEntity().setRole(RoleEnum.ADMIN),
                                new RoleEntity().setRole(RoleEnum.USER)
                        )
                );

        when(mockUserRepository.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        //act
        AppUserDetails userDetails = (AppUserDetails) toTest.loadUserByUsername(testUserEntity.getEmail());

        //assert
        Assertions.assertEquals(testUserEntity.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + RoleEnum.ADMIN.name(), authoritiesIter.next().getAuthority());
        Assertions.assertEquals("ROLE_" + RoleEnum.USER.name(), authoritiesIter.next().getAuthority());


    }

    @Test
    void testLoadUserByUsername_UserDoesNotExists() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("not-existant@example.com"));
    }
}
