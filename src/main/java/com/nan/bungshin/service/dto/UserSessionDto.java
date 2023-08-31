package com.nan.bungshin.service.dto;

import com.nan.bungshin.domain.Role;
import com.nan.bungshin.domain.User;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class UserSessionDto implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    public UserSessionDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
