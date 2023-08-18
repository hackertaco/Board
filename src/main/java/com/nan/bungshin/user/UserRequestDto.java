package com.nan.bungshin.user;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDto {
    public record CheckEmailForm(@NotBlank String email) {}

    public record JoinForm(
            @NotBlank String email,
            @NotBlank String password
    ){}
    public record LoginForm(
            @NotBlank String email,
            @NotBlank String password
    ){}
}
