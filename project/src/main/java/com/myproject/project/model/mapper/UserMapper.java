package com.myproject.project.model.mapper;

import com.myproject.project.model.dto.UserProfileEditViewModel;
import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegistrationDto.getPassword()))")
    public abstract UserEntity userRegistrationDtoToUserEntity(UserRegistrationDto userRegistrationDto);

    public abstract UserViewModel userEntityToUserViewModel(UserEntity user);

    public abstract UserProfileEditViewModel userEntityToUserProfileEditViewModel(UserEntity user);

}
