package com.nan.bungshin.global.security.filter;

import com.nan.bungshin.global.security.constant.JwtConstant;
import com.nan.bungshin.global.security.jwt.JwtProvider;
import com.nan.bungshin.global.security.jwt.RefreshTokenProvider;
import com.nan.bungshin.global.util.CookieUtils;
import com.nan.bungshin.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = accessTokenProvider.loadToken(request);
        String refreshToken = refreshTokenProvider.loadToken(request);
        if(StringUtils.hasLength(accessToken) || StringUtils.hasLength(refreshToken)){
            if(accessTokenProvider.shouldReissue(accessToken) && refreshTokenProvider.checkValidity(refreshToken)){
                if(authService.checkExistRefreshTokenOnDB(refreshToken)){
                    Claims claims = null;
                    try {
                        claims = refreshTokenProvider.getClaims(refreshToken);
                    } catch (SignatureException e) {
                        throw new RuntimeException(e);
                    }
                    String newAccessToken = accessTokenProvider.issueToken(claims);
                    response.addHeader(JwtConstant.ACCESS_TOKEN_FIELD, JwtConstant.HEADER_PREFIX + newAccessToken);
                    accessToken = newAccessToken;
                    if (refreshTokenProvider.shouldReissue(refreshToken)) {
                        String newRefreshToken = authService.reissueRefreshToken(refreshToken);
                        response.addCookie(CookieUtils.createRefreshTokenCookie(newRefreshToken));
                    }
                } else {
                    response.addCookie(CookieUtils.createRefreshTokenCookie(null));
                }
            }
            if(accessTokenProvider.checkValidity(accessToken)) {
                Claims claims = null;
                try {
                    claims = accessTokenProvider.getClaims(accessToken);
                } catch (SignatureException e) {
                    throw new RuntimeException(e);
                }
                Authentication authentication = authService.getAuthentication(claims.getSubject());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
