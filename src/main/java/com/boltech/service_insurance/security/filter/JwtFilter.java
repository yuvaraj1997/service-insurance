package com.boltech.service_insurance.security.filter;

import com.boltech.service_insurance.constant.JwtConstants;
import com.boltech.service_insurance.security.dto.AuthorizationToken;
import com.boltech.service_insurance.service.UserSessionService;
import com.boltech.service_insurance.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String API_TO_GET_ACCESS_TOKEN = "/auth/token";
    private static final String API_TO_LOGOUT = "/auth/logout";

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private UserSessionService userSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        Optional<Cookie> cookieToken = Optional.empty();

        if (null != request.getCookies()) {
            cookieToken = Arrays.stream(request.getCookies()).filter(cookie -> Objects.equals(cookie.getName(), "refreshToken")).findFirst();
        }

        String token = cookieToken.map(Cookie::getValue).map(t -> t.replace("Bearer ", "")).orElse(null);

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer ", "");
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthorizationToken authorizationToken = jwtUtil.parseToken(token);

        if (!jwtUtil.isTokenValid(token)) {
            userSessionService.markSessionAsInactive(authorizationToken.getSessionId());
            filterChain.doFilter(request, response);
            return;
        }

        if (!userSessionService.isSessionActive(authorizationToken.getSessionId())) {
            log.info("Session expired userId={} , sessionId={}", authorizationToken.getUserId(), authorizationToken.getSessionId());
            filterChain.doFilter(request, response);
            return;
        }

        String tokenType = authorizationToken.getTokenType();

        boolean isAllowedToken = (tokenType.equals(JwtConstants.TokenType.REFRESH_TOKEN.getType()) && (uri.equals(API_TO_GET_ACCESS_TOKEN) || uri.equals(API_TO_LOGOUT))) ||
                (tokenType.equals(JwtConstants.TokenType.ACCESS_TOKEN.getType()) && !uri.equals(API_TO_GET_ACCESS_TOKEN));

        if (!isAllowedToken) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                authorizationToken, null, authorizationToken.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
