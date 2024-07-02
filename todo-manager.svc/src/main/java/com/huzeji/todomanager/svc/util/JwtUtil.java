package com.huzeji.todomanager.svc.util;

import com.huzeji.model.UserEntity;
import com.huzeji.todomanager.svc.UserSvc;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Autowired private UserSvc userSvc;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return createToken(username);
    }

    private String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //                                                   sec    mi   hr   dy
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public UserEntity getUserFromToken(String authorizationHeader) {
        if( authorizationHeader == null || !authorizationHeader.startsWith( "Bearer " ) )
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");

        String username = extractUsername(authorizationHeader.substring(7));
        Map<String, Object> filter = new HashMap<>();
        filter.put( "nickname", username );

        Page<UserEntity> userPage = userSvc.get( filter );
        if( userPage.isEmpty() ) throw new ResponseStatusException( HttpStatus.NO_CONTENT, "User not found" );
        return userPage
                .getContent()
                .stream()
                .findFirst()
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NO_CONTENT, "User not found" ) );
    }
}
