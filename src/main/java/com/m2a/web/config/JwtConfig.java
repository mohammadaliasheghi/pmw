package com.m2a.web.config;

import com.m2a.common.security.HttpUtil;
import com.m2a.web.entity.SecurityInformationEntity;
import com.m2a.web.service.SecurityInformationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtConfig {

    @Value("${security.password.secret-key}")
    private String SECRET_KEY;
    @Value("${security.token.expiration-time}")
    private Long EXPIRATION_TIME;

    private SecurityInformationService securityInformationService;

    @Autowired
    public void setUsersService(SecurityInformationService securityInformationService) {
        this.securityInformationService = securityInformationService;
    }

    public String generateToken(String username, HttpServletRequest request) {
        SecurityInformationEntity sie = securityInformationService.loadUserByUsername(username);
        long now = System.currentTimeMillis();
        // Get client information from request
        String ipAddress = HttpUtil.getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String deviceInfo = HttpUtil.extractDeviceInfo(userAgent);
        return Jwts.builder()
                .setSubject(username)
                .claim("id", sie.getId())
                .claim("username", sie.getUsername())
                .claim("authorities", sie.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                // Add security-related claims
                .claim("ip", ipAddress)
                .claim("userAgent", userAgent)
                .claim("deviceInfo", deviceInfo)
                .claim("loginTime", now)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            token = token.substring(7);
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return validateIpAddressAndUserAgent(token, request);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return false;
    }

    private boolean validateIpAddressAndUserAgent(String token, HttpServletRequest request) {
        try {
            // 1. Get client information from request
            String clientIp = HttpUtil.getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            // 2. Get claims from token
            JSONObject claims = HttpUtil.getAllClaims(token);
            // 3. Extract claims with fallbacks
            String storedUserAgent = claims.optString("userAgent",
                    claims.optString("ua",
                            claims.optString("user_agent", null)));
            String storedIp = claims.optString("ip",
                    claims.optString("ipAddress",
                            claims.optString("clientIp", null)));
            return storedIp.equals(clientIp) && storedUserAgent.equals(userAgent);
        } catch (Exception e) {
            return false;
        }
    }
}