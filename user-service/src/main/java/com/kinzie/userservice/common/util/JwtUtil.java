package com.kinzie.userservice.common.util;

import com.kinzie.userservice.common.security.entity.RefreshToken;
import com.kinzie.userservice.common.security.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey

    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 액세스 토큰 생성
    public String generateAccessToken(String accountName, String role,Long userId) {
        return createToken(accountName,userId ,role, accessTokenExpiration);
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String accountName, String role, Long userId) {
        String refreshToken = createToken(accountName, userId ,role, refreshTokenExpiration);
        RefreshToken token = new RefreshToken(userId, refreshToken);
        refreshTokenRepository.save(token);
        return refreshToken;
    }

    // 토큰 생성
    public String createToken(String accountName, Long userId, String role, long expirationTime) {
        Date date = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("username", accountName);
        claims.put("userId",userId);

        return Jwts.builder()
                        .setSubject(accountName) // 사용자 식별자값(ID)
                        .setClaims(claims)
                        .setExpiration(new Date(date.getTime() + expirationTime)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}