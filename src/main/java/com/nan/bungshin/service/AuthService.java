package com.nan.bungshin.service;

import com.nan.bungshin.domain.RefreshToken;
import com.nan.bungshin.global.security.jwt.JwtProvider;
import com.nan.bungshin.persistence.RefreshTokenRepository;
import com.nan.bungshin.service.dto.IssueTokensDto;
import com.nan.bungshin.service.dto.LoginDto;
import com.nan.bungshin.service.dto.SecuredUserDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SignatureException;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailsService userDetailsService;
    private final JwtProvider accessTokenProvider;
    private final JwtProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public Authentication getAuthentication(String userName){
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    public Authentication getAuthentication(LoginDto loginDto){
        SecuredUserDto userDetails = (SecuredUserDto) userDetailsService.loadUserByUsername(loginDto.getUserName());
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                loginDto.getPassword(),
                userDetails.getAuthorities()
        );
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
    public IssueTokensDto issueTokens(Authentication authentication){
        SecuredUserDto userDetails = (SecuredUserDto) authentication.getPrincipal();
        Claims claims = JwtProvider.createClaims(authentication.getName());
        var accessToken = accessTokenProvider.issueToken(claims);
        var refreshToken = refreshTokenProvider.issueToken(claims);

        return new IssueTokensDto(userDetails.getUserDto(), accessToken, refreshToken);
    }
    @Transactional
    public void logout(HttpServletRequest request){
        String refreshToken = refreshTokenProvider.loadToken(request);
        refreshTokenRepository.deleteByPayload(refreshToken);
    }

    public boolean checkExistRefreshTokenOnDB(String refreshToken){
        return refreshTokenRepository.findByPayload(refreshToken).isPresent();
    }
    @Transactional
    public String reissueRefreshToken(String refreshToken) {
        RefreshToken previousRefreshToken = refreshTokenRepository.findByPayload(refreshToken)
                .orElseThrow(IllegalArgumentException::new);
        try{
            checkRefreshTokenReissueAllowed(previousRefreshToken);

            var claims = refreshTokenProvider.getClaims(refreshToken);
            var newRefreshToken = refreshTokenProvider.issueToken(claims);
            previousRefreshToken.renew(newRefreshToken);

            return newRefreshToken;
        }catch (IllegalArgumentException e){
            refreshTokenRepository.delete(previousRefreshToken);
            throw e;
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }

    }
    void checkRefreshTokenReissueAllowed(RefreshToken refreshToken){
        LocalDateTime lastUsedAt = refreshToken.getLastUsedAt();
        Duration afterLastReissue = Duration.between(lastUsedAt, LocalDateTime.now());
        if(afterLastReissue.compareTo(getRefreshReissueWatingDuration()) < 0){
            throw new IllegalStateException("Refresh token reissue is not allowed within 10 minutes");
        }
    }
    Duration getRefreshReissueWatingDuration(){
        var expirationDuration = refreshTokenProvider.getExpirationDuration();
        var reissueDuration = refreshTokenProvider.getReissueDuration();

        return expirationDuration.minus(reissueDuration);
    }
}
