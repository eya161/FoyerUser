package tn.esprit.foyeruniversiteeya.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.foyeruniversiteeya.entities.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@AllArgsConstructor
@NoArgsConstructor
public class JWTService {

    @Value("${spring.jwt.secret}")
    private  String JWT_SECRET;

    @Value("${spring.jwt.jwtExpirationInMs}")
    private int JWT_EXPIRATION_TIME_IN_MILLISECONDS;

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getIdUser());
        claims.put("firstName", user.getNomUser());
        claims.put("lastName", user.getPrenomUser());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        // Add other user-related data as needed

        return tokenCreator(claims, user);
    }


    public String tokenCreator(Map<String, Object> claims, User user){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(user))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRATION_TIME_IN_MILLISECONDS))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsernameFromToken(String theToken){
        return extractClaim(theToken, Claims ::getSubject);
    }
    public Date extractExpirationTimeFromToken(String theToken) {
        return extractClaim(theToken, Claims :: getExpiration);
    }
    public String extractEmailFromToken(String theToken){
        return extractClaim(theToken, claims -> claims.get("email", String.class));
    }
    public Boolean validateToken(String theToken, UserDetails userDetails){
        final String userName = extractUsernameFromToken(theToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(theToken));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    private boolean isTokenExpired(String theToken) {
        return extractExpirationTimeFromToken(theToken).before(new Date());
    }
    private Key getSignedKey(){
        byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }

}