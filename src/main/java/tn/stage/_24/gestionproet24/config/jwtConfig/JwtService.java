package tn.stage._24.gestionproet24.config.jwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.stage._24.gestionproet24.entities.Role;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SecretKey="R7Yh3ubN7TxKY+jcIA8/j4j9bN9wncb1ox0vO2wYfP4z+h3gPkl7LHRvOjfpH";
    public String extractUsername(String token){
        return extractAllClaim(token, Claims::getSubject);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
        /*   return extractExpiration(token).after(new Date());*/
    }
    public boolean isTokenExpired2(String token) {
        Claims claims = extractClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date extractExpiration(String token) {
        return extractAllClaim(token,Claims::getExpiration);
    }

    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                /*.setExpiration(new Date(System.currentTimeMillis()+90000*60*24*356))*/
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 365 * 24 * 60 * 60 * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();


    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Role extractRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        String roleString = claims.get("role", String.class);

        // Convert the role string to the Role enum
        Role role = Role.valueOf(roleString);
        return role;
    }
    public <T> T extractAllClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Key getSignKey() {
        byte[] buffer= Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(buffer);
    }
}
