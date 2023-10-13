package com.nan.board.controller;
import com.nan.board.global.security.constant.JwtConstant;
import com.nan.board.global.util.CookieUtils;
import com.nan.board.service.AuthService;
import com.nan.board.service.UserService;
import com.nan.board.service.dto.IssueTokensDto;
import com.nan.board.service.dto.LoginDto;
import com.nan.board.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserRestController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> register(@RequestBody UserDto.Request dto){
        userService.register(dto);

        return ResponseEntity.ok(dto.getUsername());
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto.Response> login(@RequestBody LoginDto loginRequestDto, HttpServletResponse httpServletResponse){
        Authentication authentication = authService.getAuthentication(loginRequestDto);
        IssueTokensDto result = authService.issueTokens(authentication);

        String accessToken = result.getAccessToken();
        String refreshToken = result.getRefreshToken();

        setAccessTokenToHeader(httpServletResponse, accessToken);
        setRefreshTokenToCookie(httpServletResponse, refreshToken);
        return ResponseEntity.ok(result.getUserDto());
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        authService.logout(request);
        setAccessTokenToHeader(response, null);
        setRefreshTokenToCookie(response, null);
        return ResponseEntity.ok().build();
    }
    void setAccessTokenToHeader(HttpServletResponse response, String accessToken){
        response.setHeader(JwtConstant.ACCESS_TOKEN_FIELD, accessToken);
    }
    void setRefreshTokenToCookie(HttpServletResponse response, String refreshToken){
        response.addCookie(CookieUtils.createRefreshTokenCookie(refreshToken));
    }

}
