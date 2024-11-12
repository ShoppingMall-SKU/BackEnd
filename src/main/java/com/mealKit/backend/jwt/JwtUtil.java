package com.mealKit.backend.jwt;

import com.mealKit.backend.domain.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private Key key;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

//    public String getPid(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("pid", String.class);
//    }
//
//    public String getProviderType(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("ProviderType", String.class);
//    }
//
//    public String getRole(String token) {
//        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
//    }

    // 만료된 JWT 토큰에서 서명 검증 없이 Claims를 추출하는 방법
    public Claims parseClaimsWithoutSignatureValidation(String token) {
        try {
            // 서명 검증 없이 새로운 JwtParser를 생성하여 Claims 파싱
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서 Claims 추출
            return e.getClaims();  // 만료된 토큰의 경우에도 Claims 반환
        } catch (JwtException e) {
            // JWT 파싱 오류 처리
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    // pid, ProviderType, role 등의 정보 추출
    public String getPid(String token) {
        Claims claims = parseClaimsWithoutSignatureValidation(token);
        return claims.get("pid", String.class);
    }

    public String getProviderType(String token) {
        Claims claims = parseClaimsWithoutSignatureValidation(token);
        return claims.get("ProviderType", String.class);
    }

    public String getRole(String token) {
        Claims claims = parseClaimsWithoutSignatureValidation(token);
        return claims.get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String pid, String pt, String role, Long expiredMs) {
        Claims claims = Jwts.claims();

        claims.put("pid", pid);
        claims.put("ProviderType", pt);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*30L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
