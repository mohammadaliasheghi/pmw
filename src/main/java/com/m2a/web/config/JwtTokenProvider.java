package com.m2a.web.config;

import com.m2a.common.security.HttpUtil;
import com.m2a.common.security.SecurityUtil;
import com.m2a.web.entity.SecurityInformationEntity;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_PREFIX = "Bearer ";

    private final String secretKey;
    private final long expirationTime;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public JwtTokenProvider(
            @Value("${security.password.secret-key}") String secretKey,
            @Value("${security.token.expiration-time}") long expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public String generateToken(String username, HttpServletRequest request) {
        try {
            SecurityInformationEntity user = (SecurityInformationEntity) userDetailsService.loadUserByUsername(username);

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            claims.put("authorities", extractAuthorities(user));

            // Add security context
            addSecurityContext(claims, request);

            long now = System.currentTimeMillis();

            String compact = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + expirationTime))
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();

            return BEARER_PREFIX + compact;
        } catch (Exception e) {
            log.error("Error generating token for user: {}", username, e);
            throw new JwtGenerationException("Failed to generate token", e);
        }
    }

    private List<String> extractAuthorities(SecurityInformationEntity user) {
        return user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    private void addSecurityContext(Map<String, Object> claims, HttpServletRequest request) {
        String ipAddress = HttpUtil.getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");

        claims.put("ipAddress", ipAddress);
        claims.put("userAgent", userAgent);
        claims.put("deviceInfo", HttpUtil.extractDetailedDeviceInfo(userAgent));
        claims.put("loginTime", System.currentTimeMillis());
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jws<Claims> claims = SecurityUtil.parseToken(token, secretKey);
            return validateTokenContext(claims, request);
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private boolean validateTokenContext(Jws<Claims> claimsJws, HttpServletRequest request) {
        Claims claims = claimsJws.getBody();

        String currentIp = HttpUtil.getClientIpAddress(request);
        String currentUserAgent = request.getHeader("User-Agent");

        String tokenIp = claims.get("ipAddress", String.class);
        String tokenUserAgent = claims.get("userAgent", String.class);

        // Validate IP and UserAgent (you might want to make this configurable)
        if (!currentIp.equals(tokenIp)) {
            log.warn("IP address mismatch. Token IP: {}, Current IP: {}", tokenIp, currentIp);
            return false;
        }

        if (!currentUserAgent.equals(tokenUserAgent)) {
            log.warn("User-Agent mismatch");
            return false;
        }

        return true;
    }

    public static class JwtGenerationException extends RuntimeException {
        public JwtGenerationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}