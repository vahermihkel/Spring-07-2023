package ee.mihkel.webshop.security;

import com.google.common.net.HttpHeaders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // JwtAuthFilter
@Log4j2
public class TokenParser extends BasicAuthenticationFilter {

    public TokenParser(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println(request.getMethod());
//        log.info(request.getMethod());
//        log.debug("");
//        log.error("");
        log.info(request.getHeader(HttpHeaders.AUTHORIZATION));

        // 1. Token peab olemas olema
        // 2. Token peab algama Bearer'ga
        // 3. Token peab olema lahtipakitav

        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null &&
                request.getHeader(HttpHeaders.AUTHORIZATION).equals("123")) {
            log.info("SEES");
            // List list = new ArrayList();
            Authentication authentication = new UsernamePasswordAuthenticationToken("KASUTAJA1", null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null &&
                request.getHeader(HttpHeaders.AUTHORIZATION).equals("1234")) {
            log.info("SEES");
            // List list = new ArrayList();
            Authentication authentication = new UsernamePasswordAuthenticationToken("KASUTAJA2", null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        super.doFilterInternal(request, response, chain); // DEFAULT
    }
}
