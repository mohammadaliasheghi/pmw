package com.m2a.web.config;

import com.m2a.common.security.SecurityUtil;
import com.m2a.web.entity.SecurityInformationEntity;
import com.m2a.web.service.SecurityInformationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${security.password.secret-key}")
    private String secretKey;

    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityInformationService securityInformationService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = SecurityUtil.getTokenFromCurrentRequest().orElse(null);

            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token, request)) {
                authenticateUser(token);
            }
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        String username = SecurityUtil.getCurrentUsername(token, secretKey);

        if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityInformationEntity userDetails = securityInformationService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Authenticated user: {}", username);
        }
    }
}