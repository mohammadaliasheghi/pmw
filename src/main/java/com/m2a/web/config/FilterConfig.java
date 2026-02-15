package com.m2a.web.config;


import com.m2a.common.config.Constant;
import com.m2a.common.security.SecurityUtil;
import com.m2a.web.entity.SecurityInformationEntity;
import com.m2a.web.service.SecurityInformationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterConfig extends OncePerRequestFilter {

    @Value("${security.password.secret-key}")
    private String SECRET_KEY;

    private JwtConfig jwtConfig;
    private SecurityInformationService securityInformationService;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Autowired
    public void setSecurityInformationService(SecurityInformationService securityInformationService) {
        this.securityInformationService = securityInformationService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(Constant.AUTHORIZATION);

        if (token != null && jwtConfig.validateToken(token, request)) {
            token = token.substring(7);
            String username = SecurityUtil.getCurrentUsername(token, SECRET_KEY);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityInformationEntity sie = securityInformationService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(sie, null, sie.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}