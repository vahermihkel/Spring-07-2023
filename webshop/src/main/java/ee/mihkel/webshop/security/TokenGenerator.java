package ee.mihkel.webshop.security;

import ee.mihkel.webshop.dto.security.Token;
import ee.mihkel.webshop.entity.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    // HILJEM KINDLASTI appication.properties failist (ei tohi Githubi panna!!)
    private String securityKey = "c3JamEI9m0kf4NUaeb0KvrbXdTCMrDdYezn7zPmwSC0op2JaHESnuOHtcpQx1+hYyce6DbnHJ0CMoW0+vrIzf3VTUIPmJg==";

    public Token getToken(Person person) {
        Token token = new Token();

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        token.setExpiration(expiration);

        String jwtToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey)), SignatureAlgorithm.HS512)
                .setAudience("Mihkel's webshop")
                .setExpiration(expiration)
                .setSubject(person.getId().toString())
                .compact();

        token.setToken(jwtToken);

        return token;
    }

}
