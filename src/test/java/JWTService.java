package com.agave.tests.Utilities.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@SuppressWarnings("deprecation")
@Component
@RequiredArgsConstructor
public class JwtService {

    private String secretKey = "26452948404D635166546A576E5A7234753778214125442A462D4A614E645267";

    private Long jwtAccessExpiration = (long) 86400000;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long jwtRefreshExpiration;

    public String generateAccessToken(Profile profile) {
        return generateToken(profile, jwtAccessExpiration);
    }

    private String generateToken(Profile profile, Long jwtAccessExpiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("profileId", profile.getId());
        
        // System.out.println(jwtRefreshExpiration);
        // System.out.println(profile);

        return generateJwtToken(claims, profile.getUsername(), jwtAccessExpiration);
    }

    public String generateRefreshToken(Profile profile) {
        return generateToken(profile, jwtRefreshExpiration);
    }

    public String generateJwtToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails username) {
        final String tokenUsername = extractUsername(token);

        // if (!username.getUsername().equals(tokenUsername)) {
        //     throw new TokenFailed(TokenFailed.INVALID_TOKEN);
        // }
        // if (isTokenExpired(token)) {
        //     throw new TokenFailed(TokenFailed.TOKEN_EXPIRED);
        // }
        return true;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

