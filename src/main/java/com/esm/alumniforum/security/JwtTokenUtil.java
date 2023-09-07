package com.esm.alumniforum.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.esm.alumniforum.config.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtTokenUtil {

    @Value(value = "${jwt.secret-key}")
    private String jwtSecret;

    @Value(value = "${app.jwtAccessExpirationInMs}")
    private int jwtAccessExpirationInMs;

    @Value(value = "${app.jwtRefreshExpirationInMs}")
    private int jwtRefreshExpirationInMs;

    @Autowired
    CustomUserDetailService userDetailService;

    public Map<String, String> generateToken(Authentication authentication) {
       UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        Date now = new Date();
        Date accessExpiryDate = new Date(now.getTime()+ jwtAccessExpirationInMs);
        Date refreshExpiryDate = new Date(now.getTime() + jwtRefreshExpirationInMs);
        String access_token = JWT.create()
                .withSubject(principal.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(accessExpiryDate)
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(refreshExpiryDate)
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", access_token);
        tokens.put("refresh", refresh_token);

        return tokens;
    }

    public String getUserIdFromJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Map<String, String> generateNewAccessToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length()); // we are removing the Bearer word to get token
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJwt = verifier.verify(refresh_token);
                String userID = decodedJwt.getSubject();
                UserPrincipal user = (UserPrincipal) userDetailService.loadUserById(userID);

                Date now = new Date();
                Date accessExpiryDate = new Date(now.getTime() + jwtAccessExpirationInMs);

                String access_token = JWT.create()
                        .withSubject(String.valueOf(user.getId()))
                        .withExpiresAt(accessExpiryDate)
                        .withIssuer(request.getRequestURL().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                return tokens;
            } catch (Exception exception) {
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                return error;
            }
        }else{
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

