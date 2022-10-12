package com.sadegh.blogrestapi.security;

import com.sadegh.blogrestapi.BlogRestApiApplication;
import com.sadegh.blogrestapi.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //retrieve 2 properties which we add in application.properties

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;



    //generate token

    public String generateToken(Authentication authentication){

        String username=authentication.getName();

        Date currentDate=new Date();

        Date expirationDate=new Date(currentDate.getTime()+jwtExpirationInMs);


        String token=Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
        return token;

    }



    //get the username form the token
    public String getUsernameFromToken(String token){

        Claims claims=Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }




    //validate JWT Token
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT signature");
        }catch (MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT signature");
        }catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Expired JWT signature");
        }catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unsupported JWT signature");
        }catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST," JWT claims string is empty");
        }




    }
}
