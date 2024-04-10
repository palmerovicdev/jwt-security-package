package com.suehay.jwtsecuritypackage.config;

import com.suehay.jwtsecuritypackage.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private @Value("${security.jwt.expiration-millis}") int EXPIRATION;
    private @Value("${security.jwt.secret-key}") String SECRET_KEY;

    public String generateToken(User user, Map<String, Object> extraClaims) {
        return Jwts.builder()
                   .setClaims(extraClaims)
                   .setSubject(user.getUsername())
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                   .signWith(generateKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    private Key generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String getUsernameFromToken(String jwtToken) {
        return extraClaims(jwtToken)
                .getSubject();
    }

    private Claims extraClaims(String jwtToken) {
        return Jwts.parserBuilder()
                   .setSigningKey(generateKey())
                   .build()
                   .parseClaimsJws(jwtToken)
                   .getBody();
    }
}