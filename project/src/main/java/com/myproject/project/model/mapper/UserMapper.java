package com.myproject.project.model.mapper;

import com.myproject.project.model.dto.UserRegistrationDto;
import com.myproject.project.model.dto.UserViewModel;
import com.myproject.project.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected Clock clock;

    private static final String OAUTH2_DEFAULT_PASSWORD = "OAuth2_authentication";

    @Mapping(target = "password",
            qualifiedByName = "mapPassword")
    @Mapping(target = "created", expression = "java(this.getTime())")
    public abstract UserEntity userRegistrationDtoToUserEntity(UserRegistrationDto userRegistrationDto);
    public abstract UserViewModel userEntityToUserViewModel(UserEntity user);

    @Named("mapPassword")
    protected String encodePassword(String password){
        if(password.equals(OAUTH2_DEFAULT_PASSWORD)){
            return password;
        }
        return this.passwordEncoder.encode(password);
    }

    protected LocalDateTime getTime(){
        return LocalDateTime.now(clock);
    }
}
