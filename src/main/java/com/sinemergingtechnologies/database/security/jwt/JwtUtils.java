package com.sinemergingtechnologies.database.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sinemergingtechnologies.database.security.services.UserDetailsImpl;

@Component
public class JwtUtils {
    // https://github.com/auth0/java-jwt
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${sinemergingtechnologies.app.jwtSecret}")
    private String jwtSecret;

    @Value("${sinemergingtechnologies.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        try {
            Algorithm algorithm = Algorithm.HMAC512("secret");
            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("custom", "info");

            String token = JWT.create()
                    .withIssuer("auth0")
                    .withHeader(headerClaims)
                    .withSubject(userPrincipal.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            return "error creating token";
        }
//        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println(jwt.getHeader());
            System.out.println(jwt.getPayload());
            System.out.println(jwt.getSignature());
            System.out.println(jwt.getSubject());
            System.out.println(jwt.toString());
            return "username ok";
        } catch (JWTDecodeException exception){
            //Invalid token
            return "error getting username";
        }
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            String algorithmName = jwt.getAlgorithm();
            String type = jwt.getType();
            String contentType = jwt.getContentType();
            String keyId = jwt.getKeyId();
            String issuer = jwt.getIssuer();
            String subject = jwt.getSubject();
            List<String> audience = jwt.getAudience();
            Date expiresAt = jwt.getExpiresAt();
            Date notBefore = jwt.getNotBefore();
            Date issuedAt = jwt.getIssuedAt();
            String id = jwt.getId();
            Claim claim = jwt.getHeaderClaim("owner");
            Map<String, Claim> claims = jwt.getClaims();    //Key is the Claim name
            Claim claim2 = claims.get("custom");
            Claim claim3 = jwt.getClaim("custom");

            return true;
        } catch (JWTVerificationException e) {
            logger.error("Invalid JWT: {}", e.getMessage());
        }

        return false;
    }
}
