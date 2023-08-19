package ee.mihkel.webshop.security;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component // JwtAuthFilter
@Log4j2
public class TokenParser extends BasicAuthenticationFilter {
    private String securityKey = "c3JamEI9m0kf4NUaeb0KvrbXdTCMrDdYezn7zPmwSC0op2JaHESnuOHtcpQx1+hYyce6DbnHJ0CMoW0+vrIzf3VTUIPmJg==";

    public TokenParser(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(requestToken);

        // 1. Token peab olemas olema
        // 2. Token peab algama Bearer'ga
        // 3. Token peab olema lahtipakitav

        if (requestToken != null &&
                requestToken.startsWith("Bearer ")) {
            requestToken = requestToken.replace("Bearer ", "");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey)))
                    .build()
                    .parseClaimsJws(requestToken)
                    .getBody();

            String personId = claims.getSubject();

            boolean isAdmin = Boolean.parseBoolean(claims.getAudience());

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (isAdmin) {
                GrantedAuthority authority = new SimpleGrantedAuthority("admin");
                authorities.add(authority);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(personId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        super.doFilterInternal(request, response, chain); // DEFAULT
    }
}
