package br.com.udemy.security;

import br.com.udemy.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.key-assign}")
    private String keyAssign;

    // generating token
    public String generateToken (User user) {
        Long lExpiration = Long.valueOf(this.expiration);

        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(lExpiration);

        Date date = Date.from(dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.ES512, this.keyAssign)
                .compact();
    }

    private Claims getClaims ( String token ) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(this.keyAssign).parseClaimsJws(token).getBody();
    }

    public boolean isValidToken ( String token ) {
        try {
            Claims claims = this.getClaims(token);

            Date expirationDate = claims.getExpiration();

            LocalDateTime expirationDateTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(expirationDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserCredentials ( String token ) throws ExpiredJwtException {
        return (String) this.getClaims(token).getSubject();
    }
}
