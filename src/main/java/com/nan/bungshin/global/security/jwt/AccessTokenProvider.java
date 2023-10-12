package com.nan.bungshin.global.security.jwt;

import com.nan.bungshin.global.security.constant.JwtConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component("accessTokenProvider")
public class AccessTokenProvider extends JwtProvider{
    public AccessTokenProvider(
            @Value("${app.auth.accessTokenExpirationDuration}")Duration duration,
            @Value("${app.auth.accessTokenSecret}")String secret,
            @Value("${app.auth.accessTokenReissueDuration}")Duration reissueDuration
            ){
        super(duration, secret, reissueDuration);
    }

    @Override
    protected String loadTokenInternal(HttpServletRequest request) {
        return request.getHeader(JwtConstant.ACCESS_TOKEN_FIELD);
    }


}
