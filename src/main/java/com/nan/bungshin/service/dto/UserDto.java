package com.nan.bungshin.service.dto;

import com.nan.bungshin.domain.User;
import com.nan.bungshin.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

public class UserDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        @Pattern(regexp = "^[a-z0-9-_]{4,20}$", message = "id는 특수문자 제외 4-20자리")
        @NotBlank(message = "필수 입력값입니다.")
        private String username;
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
        private String nickname;
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\\\W)(?=\\\\S+$).{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(message = "필수 입력값입니다.")
        private String password;
        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        private String email;
        private Role role;
        public User toEntity(){
            return User.builder()
                    .id(id)
                    .username(username)
                    .nickname(nickname)
                    .password(password)
                    .email(email)
                    .role(role.USER)
                    .build();

        }
    }
    @Getter
    public static class Response implements Serializable {
        private final Long id;
        private final String username;
        private final String email;
        private final String nickname;
        private final Role role;
        private final String modifiedDate;

        public Response(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            this.role = user.getRole();
            this.email = user.getEmail();
            this.modifiedDate = user.getModifiedDate();
        }
    }

}
