package web.app.caravanamedieval.security.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import web.app.caravanamedieval.model.Player;


@Service
public class JwtService {

    private static Duration tokeDuration = Duration.ofHours(10);

    @Value("${jwt.signing-key}")
    private String jwtPrivateKey;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Player player) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", player.getRole().name());
        claims.put("idPlayer", player.getIdPlayer());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(player.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokeDuration.toMillis()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public boolean isTokenValid(String token, String username) {
        final String userName = extractUserName(token);
        return (userName.equals(username)) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtPrivateKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
