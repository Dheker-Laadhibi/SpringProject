package com.dheker.securityDvApp.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


         //    the signing key is the same for all tokens  It doesn’t need to change per user or per request
    private static final String SECRET_KEY="c2VjcmV0MTIzNDU2Nzg5MDEyMzQ1Ng==";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    // ✅ Extract any claim using a function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }




//Claims are the data inside the token payload.
    //we extract them to know this :  Authenticate the user  Check permissions or roles
private  Claims extractAllClaims(String  token) {

//Parsing means:
//➡Reading a structured string (like JSON, XML, or JWT)
// And extracting meaningful information from it


    //This creates a builder object for parsing JWTs
        return Jwts.parserBuilder()
                //JWTs are signed to ensure they haven’t been tampered with.
                .setSigningKey(getSinginkey())

                .build()
                //This takes the JWT string (token) and:
                //
                //Verifies its signature
                //
                //Parses the token
                .parseClaimsJws(token)
                //Gets the claims (payload) part of the JWT.
                //
                //This contains:
                //
                //Username (sub)
                //
                //Expiration time (exp)
                //
                //Roles, email, etc.
                .getBody();
}

    private  Key getSinginkey() {


        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }








    public String generateToken(

            UserDetails userDetails
    ) {
        return generateToken(new HashMap<String, Object>(), userDetails);


    }

public  Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return  username.equals(userDetails.getUsername())&& !isTokenExpired(token);
}

    private boolean isTokenExpired(String token) {

        return  extractExpiration(token).before(new  Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims) // custom claims like "role"
                .setSubject(userDetails.getUsername()) // main user identity
                .setIssuedAt(new Date(System.currentTimeMillis() ))

                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSinginkey(), SignatureAlgorithm.HS256)
                //generate and return the token
                .compact();
    }



}
