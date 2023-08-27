package ee.mihkel.webshop.configuration;

import ee.mihkel.webshop.security.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    TokenParser tokenParser;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors().and().headers().xssProtection().disable().and()
                .csrf().disable()
                .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
                    .oauth2Login(Customizer.withDefaults()).build();
//                .cors(AbstractHttpConfigurer::disable)
//                .headers(headers -> headers
//                        .xssProtection(HeadersConfigurer.XXssConfig::disable
//                        ))
//                .csrf(AbstractHttpConfigurer::disable)
                // 1. Kui API otspunkti siia ei pane, siis pääseb igasuguse valiidse tokeniga ligi
                // 2. Kui API otspunktil on .permitAll() siis pääseb igal juhul ligi
                // 3. Kui API otspunktil on .hasRole() siis pääseb ligi ainult kui sellel
                //              authoritiel on see roll määratud
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/public-products").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/signup").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/categories").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/categories").hasAuthority("admin")
//                        .requestMatchers(HttpMethod.DELETE,"/categories").hasAuthority("admin")
//                        .requestMatchers(HttpMethod.PUT,"/categories").hasAuthority("admin")
//                        .requestMatchers("/persons").hasAuthority("admin")
//                        .requestMatchers("/products").hasAuthority("admin")
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                // jwtAuthFilter
//                //
//                .addFilterBefore(tokenParser, BasicAuthenticationFilter.class)
//                .build();

    }

//    http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> {
//        auth.requestMatchers("/auth/**").permitAll();
//        auth.requestMatchers("/admin/**").hasRole("ADMIN");
//        auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER");
//        auth.anyRequest().authenticated();
//    });
}
