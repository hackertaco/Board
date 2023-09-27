package com.nan.bungshin.global.security.jwt;

import com.nan.bungshin.global.security.constant.JwtConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.security.SignatureException;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public abstract class JwtProvider {
    @Getter
    private final Duration expirationDuration;
    @Getter
    private final Duration reissueDuration;
    private final Key key;
    private final JwtParser parser;
    public JwtProvider(Duration expirationDuration, String secret, Duration reissueDuration){
        this.expirationDuration = expirationDuration;
        this.reissueDuration = reissueDuration;
        this.key = createKey(secret);
        this.parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }
    public static Claims createClaims(String subject){
        var claims = Jwts.claims();
        claims.setSubject(subject);
        return claims;
    }
    public Claims getClaims(String token) throws
            ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException {
        return parser.parseClaimsJwt(token).getBody();
    }
    public String issueToken(Claims claims) {
        Date expiration = new Date(System.currentTimeMillis() + expirationDuration.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();
    }
    public String loadToken(HttpServletRequest request){
        String payload = loadTokenInternal(request);
        if(StringUtils.hasLength(payload) && payload.startsWith(JwtConstant.HEADER_PREFIX)){
            return payload.replace(JwtConstant.HEADER_PREFIX, "");
        }
        return null;
    }
    public boolean checkValidity(String token){
        try {
            getClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public boolean shouldReissue(Claims claims){
        Date reissueDate = new Date(System.currentTimeMillis() + reissueDuration.toMillis());
        return claims.getExpiration().before(reissueDate);
    }
    public boolean shouldReissue(String token){
        try {
            Claims claims = getClaims(token);
            return shouldReissue(claims);
        } catch (Exception e){
            return false;
        }
    }
    protected abstract String loadTokenInternal(HttpServletRequest request);
    private Key createKey(String secret){
        var secretBytes = Base64.getEncoder().encode(secret.getBytes());
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
