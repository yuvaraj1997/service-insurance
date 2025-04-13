package com.boltech.service_insurance.security.filter;

import com.boltech.service_insurance.constant.ErrorCode;
import com.boltech.service_insurance.exception.ErrorResponse;
import com.boltech.service_insurance.security.dto.TokenResult;
import com.boltech.service_insurance.security.dto.UserLoginRequest;
import com.boltech.service_insurance.service.UserSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.net.URL;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String baseUrlUi;

    private ObjectMapper objectMapper;

    private UserSessionService userSessionService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserSessionService userSessionService, ObjectMapper objectMapper, String baseUrlUi) {
        super(authenticationManager);
        this.userSessionService = userSessionService;
        this.objectMapper = objectMapper;
        this.baseUrlUi = baseUrlUi;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequest userLoginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userLoginRequest.getEmail(),
                    userLoginRequest.getPassword()
            );

            return getAuthenticationManager().authenticate(auth);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_VALUE);

        try {
            if (!authResult.isAuthenticated()) {
                chain.doFilter(request, response);
            }

            User user = (User) authResult.getPrincipal();
            TokenResult tokenResult = userSessionService.generateRefreshToken(user.getUsername());

            // Set the refresh token as a secure HttpOnly cookie
            String refreshToken = tokenResult.getRefreshToken();
            int maxAge = 2592000; // 30 days in seconds

            URL url = new URL(baseUrlUi);

            // Set the cookie
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // Set to true if using HTTPS
            cookie.setMaxAge(maxAge);
            cookie.setPath("/"); // Cookie will be available for all paths
            cookie.setDomain(url.getHost()); // Set the domain, can be your server's domain

            // Add the cookie to the response
            response.addCookie(cookie);
        } catch (Exception ex) {
            log.error("[{}]: Unable to proceed with successful authentication errorMessage={}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new AuthenticationServiceException("Internal Server Error. Please contact support.", ex);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_VALUE);

        if (failed.getCause() instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_CREDENTIALS, request.getRequestURI());
            objectMapper.writeValue(response.getOutputStream(), errorResponse);
            return;
        }

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI());
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
