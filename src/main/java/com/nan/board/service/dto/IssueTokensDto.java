package com.nan.board.service.dto;

import lombok.Getter;

@Getter
public class IssueTokensDto {
    private UserDto.Response userDto;
    private String accessToken;
    private String refreshToken;
    public IssueTokensDto(UserDto.Response userDto, String accessToken, String refreshToken){
        this.userDto = userDto;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
