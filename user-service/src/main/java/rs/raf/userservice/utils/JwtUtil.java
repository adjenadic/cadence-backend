package rs.raf.userservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rs.raf.userservice.data.dtos.ResponseUserDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "secret";

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails user) {
        return (user.getUsername().equals(getEmail(token)) && !isTokenExpired(token));
    }

    public String getEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getPermissions(String token) {
        return extractAllClaims(token).get("permissions", String.class);
    }

    public String generateToken(ResponseUserDto responseUserDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", responseUserDto.getId());
        claims.put("email", responseUserDto.getEmail());
        claims.put("permissions", responseUserDto.getPermissions());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(responseUserDto.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }
}
