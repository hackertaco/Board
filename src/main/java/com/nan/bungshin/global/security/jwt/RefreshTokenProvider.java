package com.nan.bungshin.global.security.jwt;

import com.nan.bungshin.global.security.constant.JwtConstant;
import com.nan.bungshin.global.util.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Component("refreshTokenProvider")
public class RefreshTokenProvider extends JwtProvider{
    public RefreshTokenProvider(
            @Value("${app.auth.refreshTokenExpirationDuration}") Duration duration,
            @Value("${app.auth.refreshTokenExpirationSecret}") String secret,
            @Value("${app.auth.refreshTokenReissueDuration}") Duration reissueDuration
            ){
        super(duration, secret, reissueDuration);
    }

    @Override
    protected String loadTokenInternal(HttpServletRequest request) {
        var refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> cookie.getName().equals(JwtConstant.REFRESH_TOKEN_FIELD))
                .findFirst();
        if(refreshToken.isPresent()){
            String payload = refreshToken.get().getValue();
            return CookieUtils.decode(payload);
        }
        return null;
    }

}
