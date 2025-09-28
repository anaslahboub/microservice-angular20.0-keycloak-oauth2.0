package com.anas.authservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test(Authentication authentication, @AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "JWT Authentication successful!");
        response.put("username", jwt.getClaimAsString("preferred_username"));
        response.put("email", jwt.getClaimAsString("email"));
        response.put("name", jwt.getClaimAsString("name"));
        response.put("roles", jwt.getClaimAsMap("realm_access").get("roles"));
        response.put("authorities", authentication.getAuthorities());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/profile")
    public ResponseEntity<Map<String, Object>> userProfile(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("sub", jwt.getSubject());
        profile.put("username", jwt.getClaimAsString("preferred_username"));
        profile.put("email", jwt.getClaimAsString("email"));
        profile.put("name", jwt.getClaimAsString("name"));
        profile.put("given_name", jwt.getClaimAsString("given_name"));
        profile.put("family_name", jwt.getClaimAsString("family_name"));
        profile.put("email_verified", jwt.getClaimAsBoolean("email_verified"));
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Map<String, Object>> adminEndpoint(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin access granted!");
        response.put("admin", jwt.getClaimAsString("preferred_username"));
        response.put("roles", jwt.getClaimAsMap("realm_access").get("roles"));
        return ResponseEntity.ok(response);
    }
}