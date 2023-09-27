package com.nan.bungshin.service.dto;

import com.nan.bungshin.domain.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

public class SecuredUserDto extends org.springframework.security.core.userdetails.User {
    @Getter
    private final UserDto.Response userDto;
    public SecuredUserDto(User user){
        super(
                user.getUsername(),
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );
        this.userDto = new UserDto.Response(user);
    }
}
