package com.anas.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/", "/webjars/**", "/login/**", "/error", 
                                "/css/**", "/js/**", "/auth", "/public/**").permitAll()
                        // API endpoints requiring authentication
                        .requestMatchers("/api/**").authenticated()
                        // Admin endpoints requiring ADMIN role
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // User endpoints requiring USER role
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                // OAuth2 Login for web-based authentication
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )
                // JWT Resource Server for API authentication
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/?logout=true")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        // Create converter for extracting authorities from JWT
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
        
        // Create custom converter to handle Keycloak roles
        Converter<Jwt, Collection<GrantedAuthority>> keycloakAuthoritiesConverter = jwt -> {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess != null && realmAccess.get("roles") != null) {
                List<String> roles = (List<String>) realmAccess.get("roles");
                return roles.stream()
                        .map(role -> "ROLE_" + role)
                        .map(role -> (GrantedAuthority) () -> role)
                        .toList();
            }
            return List.of();
        };
        
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(keycloakAuthoritiesConverter);
        
        return converter;
    }
}