package com.myproject.project.model.mapper;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserRegistrationDto userRegistrationDto);

}
