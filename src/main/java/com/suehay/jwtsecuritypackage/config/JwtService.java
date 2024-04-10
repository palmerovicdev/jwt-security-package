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

    /**
     * This method is used to generate a JWT token for a given user with additional claims.
     * The token is signed with a secret key and has an expiration time.
     *
     * @param user        the User entity for which the token is generated. The username of the user is set as the subject of the JWT token.
     * @param extraClaims a Map of additional claims to be included in the JWT token.
     * @return a String representing the JWT token.
     */
    public String generateToken(User user, Map<String, Object> extraClaims) {
        return Jwts.builder()
                   .setClaims(extraClaims)
                   .setSubject(user.getUsername())
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                   .signWith(generateKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    /**
     * This method is used to generate a SecretKey for signing the JWT token.
     * It uses the HMAC-SHA algorithm for the key and decodes the secret key from Base64.
     *
     * @return a SecretKey that can be used to sign a JWT token.
     */
    private Key generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    /**
     * This method is used to extract the username from a given JWT token.
     * It uses the extraClaims method to parse the JWT token and retrieve the claims.
     * The subject of the JWT token, which is the username, is then returned.
     *
     * @param jwtToken the JWT token from which the username is to be extracted.
     * @return a String representing the username.
     */
    public String getUsernameFromToken(String jwtToken) {
        return extraClaims(jwtToken)
                .getSubject();
    }

    /**
     * This method is used to parse the JWT token and retrieve the claims.
     * It uses the parserBuilder method from the Jwts class to create a JwtParserBuilder.
     * The signing key for the parser is set using the generateKey method.
     * The JwtParserBuilder is then built into a JwtParser.
     * The parseClaimsJws method is used to parse the JWT token into a Jws<Claims> object.
     * The body of the Jws<Claims> object, which contains the claims, is then returned.
     *
     * @param jwtToken the JWT token to be parsed.
     * @return a Claims object containing the claims from the JWT token.
     */
    private Claims extraClaims(String jwtToken) {
        return Jwts.parserBuilder()
                   .setSigningKey(generateKey())
                   .build()
                   .parseClaimsJws(jwtToken)
                   .getBody();
    }
}