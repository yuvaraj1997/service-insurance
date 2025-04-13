package com.boltech.service_insurance.controller;

import com.boltech.service_insurance.dto.SignUpUserRequest;
import com.boltech.service_insurance.security.dto.AuthorizationToken;
import com.boltech.service_insurance.service.UserManagementService;
import com.boltech.service_insurance.service.UserSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Auth")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    UserSessionService userSessionService;

    @Operation(operationId = "Sign Up", summary = "Sign Up")
    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid SignUpUserRequest signUpUserRequest) {
        userManagementService.signUp(signUpUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(operationId = "Get Access Token", summary = "Get Access Token")
    @GetMapping("/token")
    public ResponseEntity<?> getAccessToken(@AuthenticationPrincipal AuthorizationToken authorizationToken) throws IOException {
        return ResponseEntity.ok().body(userSessionService.generateAccessToken(authorizationToken));
    }

    @Operation(operationId = "Logout", summary = "Logout")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal AuthorizationToken authorizationToken, HttpServletResponse response) throws IOException {
        userSessionService.markSessionAsInactive(authorizationToken.getSessionId());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.noContent().build();
    }
}
